package com.mezo.chaos.nyx.excel.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface StudentService {
    /**
     * 倒入Excel
     * @param myFile
     * @return
     * @throws Exception
     */
    Integer importExcel(MultipartFile myFile) throws Exception;

    /**
     * 导出Excel
     * @param response
     * @throws Exception
     */
    void exportExcel(HttpServletResponse response) throws Exception;
}
