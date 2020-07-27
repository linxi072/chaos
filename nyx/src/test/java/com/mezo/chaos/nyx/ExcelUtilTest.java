package com.mezo.chaos.nyx;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.util.FileUtils;
import com.sun.crypto.provider.AESKeyGenerator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.poifs.crypt.CryptoFunctions;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionVerifier;
import org.apache.poi.poifs.crypt.standard.StandardEncryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import sun.misc.BASE64Encoder;
import com.jxcell.CellException;
import com.jxcell.View;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

public class ExcelUtilTest {

    @Test
    public void test01() throws IOException, GeneralSecurityException {
        String excelPath = "/Users/mezo/workstation/abcd.xlsx";
        Workbook workbook;
        InputStream inp = new FileInputStream(excelPath);
        //解密
        POIFSFileSystem pfs = new POIFSFileSystem(inp);
        inp.close();
        EncryptionInfo encInfo = new EncryptionInfo(pfs);
        Decryptor decryptor = Decryptor.getInstance(encInfo);
        decryptor.verifyPassword("1234");
        workbook = new XSSFWorkbook(decryptor.getDataStream(pfs));
        Sheet sheet = workbook.getSheet(String.valueOf(1));
        String sheetName = sheet.getSheetName();
        System.out.println(sheetName);
    }

    @Test
    public void test02(){
        byte[] bytes ={-11,54,17,-37,-6,28,-2,65,-78,37,-125,-40,53,-64,-87,-91};
        byte[] bytes1={122,-100,-78,-28,1,64,0,-116,11,-102,78,14,-54,124,-118,108};
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(bytes);
        System.out.println(encode);
    }

    @Test
    public void test03() {
        String excelPath = "/Users/mezo/workstation/abcd.xlsx";
        View m_view = new View();
        try {
            // read the encrypted excel file
            m_view.read(excelPath, "1234");
            // write without password protected
            m_view.write(excelPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
