/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author fcarv
 */
public class CriptografiaAES {
    private static final String ALGO = "AES";
    private byte[] Token;
    
    public CriptografiaAES(String Tok){
        Token = Tok.getBytes();
    }
    
    public String Encriptar(String Data) throws Exception{
        Key key = generateKey();
        Cipher C = Cipher.getInstance(ALGO);
        C.init(Cipher.ENCRYPT_MODE, key);
        byte[] EncVal = C.doFinal(Data.getBytes());
        String ValEncriptado = new BASE64Encoder().encode(EncVal);
        return ValEncriptado;
    }
    
    public String DesEncriptar(String Encrip) throws Exception{
        Key key = generateKey();
        Cipher C = Cipher.getInstance(ALGO);
        C.init(Cipher.DECRYPT_MODE, key);
        byte[] ValDeco = new BASE64Decoder().decodeBuffer(Encrip);
        byte[] DecVal = C.doFinal(ValDeco);
        String ValDecodificado = new String(DecVal);
        return ValDecodificado;
    }
    
    private Key generateKey() throws Exception{
        Key key = new SecretKeySpec(Token,ALGO);
        return key;
    }
}
