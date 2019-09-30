package com.taoboot.mini.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.logging.Logger;

public class Base64
{
    static Logger log = Logger.getLogger(Base64.class.getName());
    public static final int BASE64DEFAULTLENGTH = 76;
    static int _base64length = 76;
    private static final int BASELENGTH = 255;
    private static final int LOOKUPLENGTH = 64;
    private static final int TWENTYFOURBITGROUP = 24;
    private static final int EIGHTBIT = 8;
    private static final int SIXTEENBIT = 16;
    private static final int FOURBYTE = 4;
    private static final int SIGN = -128;
    private static final char PAD = '=';
    private static final boolean fDebug = false;

    public static BigInteger decodeBigIntegerFromElement(Element element)
            throws Exception
    {
        return new BigInteger(1, decode(element));
    }

    public static BigInteger decodeBigIntegerFromText(Text text)
            throws Exception
    {
        return new BigInteger(1, decode(text.getData()));
    }

    public static byte[] decode(Element element)
            throws Exception
    {
        Node sibling = element.getFirstChild();
        StringBuffer sb = new StringBuffer();
        while (sibling != null)
        {
            if (sibling.getNodeType() == 3)
            {
                Text t = (Text)sibling;

                sb.append(t.getData());
            }
            sibling = sibling.getNextSibling();
        }
        return decode(sb.toString());
    }

    public static byte[] decode(byte[] base64)
            throws Exception
    {
        return decodeInternal(base64);
    }

    public static String encode(byte[] binaryData)
    {
        return encode(binaryData, 76);
    }

    public static void main(String[] args)
            throws Exception
    {
        DocumentBuilderFactory docBuilderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        String testString1 =
                "<container><base64 value=\"Should be 'Hallo'\">SGFsbG8=</base64></container>";
        InputSource inputSource = new InputSource(new StringReader(testString1));
        Document doc = docBuilder.parse(inputSource);
        Element base64Elem =
                (Element)doc.getDocumentElement().getChildNodes().item(0);

        System.out.println(new String(decode(base64Elem)));
    }

    private static final byte[] base64Alphabet = new byte['ÿ'];
    private static final char[] lookUpBase64Alphabet = new char[64];

    static
    {
        for (int i = 0; i < 255; i++) {
            base64Alphabet[i] = -1;
        }
        for (int i = 90; i >= 65; i--) {
            base64Alphabet[i] = ((byte)(i - 65));
        }
        for (int i = 122; i >= 97; i--) {
            base64Alphabet[i] = ((byte)(i - 97 + 26));
        }
        for (int i = 57; i >= 48; i--) {
            base64Alphabet[i] = ((byte)(i - 48 + 52));
        }
        base64Alphabet[43] = 62;
        base64Alphabet[47] = 63;
        for (int i = 0; i <= 25; i++) {
            lookUpBase64Alphabet[i] = ((char)(65 + i));
        }
        int i = 26;
        for (int j = 0; i <= 51; j++)
        {
            lookUpBase64Alphabet[i] = ((char)(97 + j));i++;
        }
        int _i = 52;
        for (int j = 0; _i <= 61; j++)
        {
            lookUpBase64Alphabet[i] = ((char)(48 + j));_i++;
        }
        lookUpBase64Alphabet[62] = '+';
        lookUpBase64Alphabet[63] = '/';
    }

    protected static final boolean isWhiteSpace(byte octect)
    {
        return (octect == 32) || (octect == 13) || (octect == 10) || (octect == 9);
    }

    protected static final boolean isPad(byte octect)
    {
        return octect == 61;
    }

    public static String encode(byte[] binaryData, int length)
    {
        if (length < 4) {
            length = 2147483647;
        }
        if (binaryData == null) {
            return null;
        }
        int lengthDataBits = binaryData.length * 8;
        if (lengthDataBits == 0) {
            return "";
        }
        int fewerThan24bits = lengthDataBits % 24;
        int numberTriplets = lengthDataBits / 24;
        int numberQuartet = fewerThan24bits != 0 ? numberTriplets + 1 : numberTriplets;
        int quartesPerLine = length / 4;
        int numberLines = (numberQuartet - 1) / quartesPerLine;
        char[] encodedData = null;

        encodedData = new char[numberQuartet * 4 + numberLines];

        byte k = 0;byte l = 0;byte b1 = 0;byte b2 = 0;byte b3 = 0;

        int encodedIndex = 0;
        int dataIndex = 0;
        int i = 0;
        for (int line = 0; line < numberLines; line++)
        {
            for (int quartet = 0; quartet < 19; quartet++)
            {
                b1 = binaryData[(dataIndex++)];
                b2 = binaryData[(dataIndex++)];
                b3 = binaryData[(dataIndex++)];





                l = (byte)(b2 & 0xF);
                k = (byte)(b1 & 0x3);

                byte val1 = (b1 & 0xFFFFFF80) == 0 ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);

                byte val2 = (b2 & 0xFFFFFF80) == 0 ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
                byte val3 = (b3 & 0xFFFFFF80) == 0 ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);







                encodedData[(encodedIndex++)] = lookUpBase64Alphabet[val1];
                encodedData[(encodedIndex++)] = lookUpBase64Alphabet[(val2 | k << 4)];
                encodedData[(encodedIndex++)] = lookUpBase64Alphabet[(l << 2 | val3)];
                encodedData[(encodedIndex++)] = lookUpBase64Alphabet[(b3 & 0x3F)];

                i++;
            }
            encodedData[(encodedIndex++)] = '\n';
        }
        for (; i < numberTriplets; i++)
        {
            b1 = binaryData[(dataIndex++)];
            b2 = binaryData[(dataIndex++)];
            b3 = binaryData[(dataIndex++)];





            l = (byte)(b2 & 0xF);
            k = (byte)(b1 & 0x3);

            byte val1 = (b1 & 0xFFFFFF80) == 0 ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);

            byte val2 = (b2 & 0xFFFFFF80) == 0 ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
            byte val3 = (b3 & 0xFFFFFF80) == 0 ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);







            encodedData[(encodedIndex++)] = lookUpBase64Alphabet[val1];
            encodedData[(encodedIndex++)] = lookUpBase64Alphabet[(val2 | k << 4)];
            encodedData[(encodedIndex++)] = lookUpBase64Alphabet[(l << 2 | val3)];
            encodedData[(encodedIndex++)] = lookUpBase64Alphabet[(b3 & 0x3F)];
        }
        if (fewerThan24bits == 8)
        {
            b1 = binaryData[dataIndex];
            k = (byte)(b1 & 0x3);




            byte val1 = (b1 & 0xFFFFFF80) == 0 ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
            encodedData[(encodedIndex++)] = lookUpBase64Alphabet[val1];
            encodedData[(encodedIndex++)] = lookUpBase64Alphabet[(k << 4)];
            encodedData[(encodedIndex++)] = '=';
            encodedData[(encodedIndex++)] = '=';
        }
        else if (fewerThan24bits == 16)
        {
            b1 = binaryData[dataIndex];
            b2 = binaryData[(dataIndex + 1)];
            l = (byte)(b2 & 0xF);
            k = (byte)(b1 & 0x3);

            byte val1 = (b1 & 0xFFFFFF80) == 0 ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
            byte val2 = (b2 & 0xFFFFFF80) == 0 ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);

            encodedData[(encodedIndex++)] = lookUpBase64Alphabet[val1];
            encodedData[(encodedIndex++)] = lookUpBase64Alphabet[(val2 | k << 4)];
            encodedData[(encodedIndex++)] = lookUpBase64Alphabet[(l << 2)];
            encodedData[(encodedIndex++)] = '=';
        }
        return new String(encodedData);
    }

    public static final byte[] decode(String encoded)
            throws Exception
    {
        if (encoded == null) {
            return null;
        }
        return decodeInternal(encoded.getBytes());
    }

    protected static final byte[] decodeInternal(byte[] base64Data)
            throws Exception
    {
        int len = removeWhiteSpace(base64Data);
        if (len % 4 != 0) {
            throw new Exception("decoding.divisible.four");
        }
        int numberQuadruple = len / 4;
        if (numberQuadruple == 0) {
            return new byte[0];
        }
        byte[] decodedData = null;
        byte b1 = 0;byte b2 = 0;byte b3 = 0;byte b4 = 0;


        int i = 0;
        int encodedIndex = 0;
        int dataIndex = 0;


        dataIndex = (numberQuadruple - 1) * 4;
        encodedIndex = (numberQuadruple - 1) * 3;

        b1 = base64Alphabet[base64Data[(dataIndex++)]];
        b2 = base64Alphabet[base64Data[(dataIndex++)]];
        if ((b1 == -1) || (b2 == -1)) {
            throw new Exception("decoding.general");
        }
        byte d3;
        b3 = base64Alphabet[(d3 = base64Data[(dataIndex++)])];
        byte d4;
        b4 = base64Alphabet[(d4 = base64Data[(dataIndex++)])];
        if ((b3 == -1) || (b4 == -1))
        {
            if ((isPad(d3)) && (isPad(d4)))
            {
                if ((b2 & 0xF) != 0) {
                    throw new Exception("decoding.general");
                }
                decodedData = new byte[encodedIndex + 1];
                decodedData[encodedIndex] = ((byte)(b1 << 2 | b2 >> 4));
            }
            else if ((!isPad(d3)) && (isPad(d4)))
            {
                if ((b3 & 0x3) != 0) {
                    throw new Exception("decoding.general");
                }
                decodedData = new byte[encodedIndex + 2];
                decodedData[(encodedIndex++)] = ((byte)(b1 << 2 | b2 >> 4));
                decodedData[encodedIndex] = ((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
            }
            else
            {
                throw new Exception("decoding.general");
            }
        }
        else
        {
            decodedData = new byte[encodedIndex + 3];
            decodedData[(encodedIndex++)] = ((byte)(b1 << 2 | b2 >> 4));
            decodedData[(encodedIndex++)] = ((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
            decodedData[(encodedIndex++)] = ((byte)(b3 << 6 | b4));
        }
        encodedIndex = 0;
        dataIndex = 0;
        for (i = numberQuadruple - 1; i > 0; i--)
        {
            b1 = base64Alphabet[base64Data[(dataIndex++)]];
            b2 = base64Alphabet[base64Data[(dataIndex++)]];
            b3 = base64Alphabet[base64Data[(dataIndex++)]];
            b4 = base64Alphabet[base64Data[(dataIndex++)]];
            if ((b1 == -1) ||
                    (b2 == -1) ||
                    (b3 == -1) ||
                    (b4 == -1)) {
                throw new Exception("decoding.general");
            }
            decodedData[(encodedIndex++)] = ((byte)(b1 << 2 | b2 >> 4));
            decodedData[(encodedIndex++)] = ((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
            decodedData[(encodedIndex++)] = ((byte)(b3 << 6 | b4));
        }
        return decodedData;
    }

    public static final void decode(byte[] base64Data, OutputStream os)
            throws Exception, IOException
    {
        int len = removeWhiteSpace(base64Data);
        if (len % 4 != 0) {
            throw new Exception("decoding.divisible.four");
        }
        int numberQuadruple = len / 4;
        if (numberQuadruple == 0) {
            return;
        }
        byte b1 = 0;byte b2 = 0;byte b3 = 0;byte b4 = 0;

        int i = 0;

        int dataIndex = 0;
        for (i = numberQuadruple - 1; i > 0; i--)
        {
            b1 = base64Alphabet[base64Data[(dataIndex++)]];
            b2 = base64Alphabet[base64Data[(dataIndex++)]];
            b3 = base64Alphabet[base64Data[(dataIndex++)]];
            b4 = base64Alphabet[base64Data[(dataIndex++)]];
            if ((b1 == -1) ||
                    (b2 == -1) ||
                    (b3 == -1) ||
                    (b4 == -1)) {
                throw new Exception("decoding.general");
            }
            os.write((byte)(b1 << 2 | b2 >> 4));
            os.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
            os.write((byte)(b3 << 6 | b4));
        }
        b1 = base64Alphabet[base64Data[(dataIndex++)]];
        b2 = base64Alphabet[base64Data[(dataIndex++)]];
        if ((b1 == -1) ||
                (b2 == -1)) {
            throw new Exception("decoding.general");
        }
        byte d3;
        b3 = base64Alphabet[(d3 = base64Data[(dataIndex++)])];
        byte d4;
        b4 = base64Alphabet[(d4 = base64Data[(dataIndex++)])];
        if ((b3 == -1) ||
                (b4 == -1))
        {
            if ((isPad(d3)) && (isPad(d4)))
            {
                if ((b2 & 0xF) != 0) {
                    throw new Exception("decoding.general");
                }
                os.write((byte)(b1 << 2 | b2 >> 4));
            }
            else if ((!isPad(d3)) && (isPad(d4)))
            {
                if ((b3 & 0x3) != 0) {
                    throw new Exception("decoding.general");
                }
                os.write((byte)(b1 << 2 | b2 >> 4));
                os.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
            }
            else
            {
                throw new Exception("decoding.general");
            }
        }
        else
        {
            os.write((byte)(b1 << 2 | b2 >> 4));
            os.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
            os.write((byte)(b3 << 6 | b4));
        }
    }

    public static final void decode(InputStream is, OutputStream os)
            throws Exception, IOException
    {
        byte b1 = 0;byte b2 = 0;byte b3 = 0;byte b4 = 0;

        int index = 0;
        byte[] data = new byte[4];
        int read;
        while ((read = is.read()) > 0)
        {
            //int read;
            byte readed = (byte)read;
            if (!isWhiteSpace(readed))
            {
                if (isPad(readed))
                {
                    data[(index++)] = readed;
                    if (index != 3) {
                        break;
                    }
                    data[(index++)] = ((byte)is.read());
                    break;
                }
                if ((data[(index++)] = readed) == -1) {
                    throw new Exception("decoding.general");
                }
                if (index == 4)
                {
                    index = 0;
                    b1 = base64Alphabet[data[0]];
                    b2 = base64Alphabet[data[1]];
                    b3 = base64Alphabet[data[2]];
                    b4 = base64Alphabet[data[3]];

                    os.write((byte)(b1 << 2 | b2 >> 4));
                    os.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
                    os.write((byte)(b3 << 6 | b4));
                }
            }
        }
        byte d1 = data[0];byte d2 = data[1];byte d3 = data[2];byte d4 = data[3];
        b1 = base64Alphabet[d1];
        b2 = base64Alphabet[d2];
        b3 = base64Alphabet[d3];
        b4 = base64Alphabet[d4];
        if ((b3 == -1) ||
                (b4 == -1))
        {
            if ((isPad(d3)) && (isPad(d4)))
            {
                if ((b2 & 0xF) != 0) {
                    throw new Exception("decoding.general");
                }
                os.write((byte)(b1 << 2 | b2 >> 4));
            }
            else if ((!isPad(d3)) && (isPad(d4)))
            {
                b3 = base64Alphabet[d3];
                if ((b3 & 0x3) != 0) {
                    throw new Exception("decoding.general");
                }
                os.write((byte)(b1 << 2 | b2 >> 4));
                os.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
            }
            else
            {
                throw new Exception("decoding.general");
            }
        }
        else
        {
            os.write((byte)(b1 << 2 | b2 >> 4));
            os.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
            os.write((byte)(b3 << 6 | b4));
        }
    }

    protected static int removeWhiteSpace(byte[] data)
    {
        if (data == null) {
            return 0;
        }
        int newSize = 0;
        int len = data.length;
        for (int i = 0; i < len; i++)
        {
            byte dataS = data[i];
            if (!isWhiteSpace(dataS)) {
                data[(newSize++)] = dataS;
            }
        }
        return newSize;
    }
}
