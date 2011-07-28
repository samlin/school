import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

public class keygen
{
  static byte[] EncodedPrvKey = { 48, -126, 1, 75, 2, 1, 0, 48, -126, 1, 44, 6, 7, 42, -122, 72, -50, 56, 4, 1, 48, -126, 1, 31, 2, -127, -127, 0, -3, 127, 83, -127, 29, 117, 18, 41, 82, -33, 74, -100, 46, -20, -28, -25, -10, 17, -73, 82, 60, -17, 68, 0, -61, 30, 63, -128, -74, 81, 38, 105, 69, 93, 64, 34, 81, -5, 89, 61, -115, 88, -6, -65, -59, -11, -70, 48, -10, -53, -101, 85, 108, -41, -127, 59, -128, 29, 52, 111, -14, 102, 96, -73, 107, -103, 80, -91, -92, -97, -97, -24, 4, 123, 16, 34, -62, 79, -69, -87, -41, -2, -73, -58, 27, -8, 59, 87, -25, -58, -88, -90, 21, 15, 4, -5, -125, -10, -45, -59, 30, -61, 2, 53, 84, 19, 90, 22, -111, 50, -10, 117, -13, -82, 43, 97, -41, 42, -17, -14, 34, 3, 25, -99, -47, 72, 1, -57, 2, 21, 0, -105, 96, 80, -113, 21, 35, 11, -52, -78, -110, -71, -126, -94, -21, -124, 11, -16, 88, 28, -11, 2, -127, -127, 0, -9, -31, -96, -123, -42, -101, 61, -34, -53, -68, -85, 92, 54, -72, 87, -71, 121, -108, -81, -69, -6, 58, -22, -126, -7, 87, 76, 11, 61, 7, -126, 103, 81, 89, 87, -114, -70, -44, 89, 79, -26, 113, 7, 16, -127, -128, -76, 73, 22, 113, 35, -24, 76, 40, 22, 19, -73, -49, 9, 50, -116, -56, -90, -31, 60, 22, 122, -117, 84, 124, -115, 40, -32, -93, -82, 30, 43, -77, -90, 117, -111, 110, -93, 127, 11, -6, 33, 53, 98, -15, -5, 98, 122, 1, 36, 59, -52, -92, -15, -66, -88, 81, -112, -119, -88, -125, -33, -31, 90, -27, -97, 6, -110, -117, 102, 94, -128, 123, 85, 37, 100, 1, 76, 59, -2, -49, 73, 42, 4, 22, 2, 20, 6, 38, 113, -15, -3, 10, 28, -97, 32, 77, 100, -15, -95, 97, -69, 101, -112, -128, -106, 42 };
  static byte[] license;
  static byte[] hash;

  private static char getCharInRange(int paramInt)
  {
    if ((0 <= paramInt) && (paramInt <= 9))
      return (char)(paramInt + 48);

    if ((10 <= paramInt) && (paramInt <= 35))
      return (char)(paramInt - 10 + 65);

    if ((36 <= paramInt) && (paramInt <= 61))
      return (char)(paramInt - 36 + 97);

    if (paramInt == 62)
      return '<';

    if (paramInt == 63)
      return '>';

    System.out.println("Invalid int in stream " + paramInt);
    return 'a';
  }

  private static char rndChar(int paramInt)
  {
    int i = paramInt * 6 + (int)(Math.random() * 6.0D);
    int j = ((int)(Math.random() * 2.0D) < 1) ? 1 : 0;
    return (char)(i + ((j != 0) ? 97 : 65));
  }

  public static String getString(byte[] paramArrayOfByte) {
    char[] arrayOfChar = new char[paramArrayOfByte.length * 2];
    for (int i = 0; i < paramArrayOfByte.length; ++i) {
      int j = paramArrayOfByte[i];
      if ((-128 <= j) && (j < -64)) {
        arrayOfChar[i] = rndChar(0);
        arrayOfChar[(paramArrayOfByte.length + i)] = getCharInRange(j + 128);
      }
      else if ((-64 <= j) && (j < 0)) {
        arrayOfChar[i] = rndChar(1);
        arrayOfChar[(paramArrayOfByte.length + i)] = getCharInRange(j + 64);
      }
      else if ((0 <= j) && (j < 64)) {
        arrayOfChar[i] = rndChar(2);
        arrayOfChar[(paramArrayOfByte.length + i)] = getCharInRange(j);
      }
      else if ((64 <= j) && (j < 128)) {
        arrayOfChar[i] = rndChar(3);
        arrayOfChar[(paramArrayOfByte.length + i)] = getCharInRange(j - 64);
      } else {
        System.out.println("Invalid Char in stream " + j);
      }
    }

    String str = new String(arrayOfChar);
    return str;
  }

  public static String getHashString() {
    return getString(hash);
  }

  public static String getLicenseString() {
    return getString(license);
  }

  public static String rawtoString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = getHashString();

    for (int i = str1.length() / 2; str1.length() > i; str1 = str1.substring(i)) {
      localStringBuffer.append(str1.substring(0, i));
      localStringBuffer.append("\n");
    }

    localStringBuffer.append(str1);
    localStringBuffer.append("\n");

    String str2 = getLicenseString();
    for (int i=str2.length(); str2.length() > i; str2 = str2.substring(i)) {
      localStringBuffer.append(str2.substring(0, i));
      localStringBuffer.append("\n");
    }

    localStringBuffer.append(str2);
    localStringBuffer.append("\n");
    return localStringBuffer.toString();
  }

  public static void main(String[] paramArrayOfString) throws IOException
  {
    long l1;
    try {
      int i;
      l1 = 85L;
      long l2 = System.currentTimeMillis();
      long l3 = System.currentTimeMillis();
      long l4 = 9666666L;
      String str1 = "";
      System.out.println("Keygen for Confluence.");
      System.out.print("created by TEAM dir/ZWT.");
      do {
        System.out.print("\nEnter your organization name: ");
        for (i = System.in.read(); (i != 10) && (i != 13); i = System.in.read())
          str1 = str1 + (char)i;
      }

      while (str1 == "");
      try
      {
        PKCS8EncodedKeySpec localPKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(EncodedPrvKey);
        KeyFactory localKeyFactory = KeyFactory.getInstance("DSA", "SUN");
        PrivateKey localPrivateKey = localKeyFactory.generatePrivate(localPKCS8EncodedKeySpec);
        String str2 = Long.toString(l1, 10);
        str2 = str2 + "^";
        str2 = str2 + Long.toString(l2, 10);
        str2 = str2 + "^";
        str2 = str2 + Long.toString(l3, 10);
        str2 = str2 + "^";
        str2 = str2 + str1;
        str2 = str2 + "^";
        str2 = str2 + Long.toString(l4, 10);
        byte[] arrayOfByte1 = str2.getBytes();
        Signature localSignature = Signature.getInstance("SHA1withDSA");
        localSignature.initSign(localPrivateKey);
        localSignature.update(arrayOfByte1);
        byte[] arrayOfByte2 = localSignature.sign();
        try
        {
          license = arrayOfByte1;
          hash = arrayOfByte2;
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        System.out.println("Your license key is: ");
        System.out.println(rawtoString());
      }
      catch (Exception localException1) {
        localException1.printStackTrace();
      }
    }
    catch (IOException localIOException)
    {
    }
  }
}