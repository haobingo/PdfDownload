package com.example.controller;

import cn.hutool.core.io.FileUtil;
import com.example.common.Result;
import com.example.entity.MajorTable;
import com.example.service.MajorTableService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/major")
public class MajorTableController {
    @Resource
    private MajorTableService majorTableService;

    //新增pdf记录
    @PostMapping
    public Result add(@RequestBody MajorTable majorTable) {
        majorTableService.save(majorTable);
        return Result.success();
    }

    //修改pdf记录
    @PutMapping
    public Result update(@RequestBody MajorTable majorTable) {
        majorTableService.save(majorTable);
        return Result.success();
    }

    //删除pdf记录
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        majorTableService.delete(id);
    }

    //根据id查询pdf记录
    @GetMapping("/{id}")
    public Result<MajorTable> findById(@PathVariable Integer id) {
        return Result.success(majorTableService.findById(id));
    }

    //查询所有pdf记录
    @GetMapping
    public Result<List<MajorTable>> findAll() {
        return Result.success(majorTableService.findAll());
    }

    //根据major查询对应的pdf文件
    @GetMapping("/findMajor/{major}")
    public Result<List<MajorTable>> findByMajor(@PathVariable String major) {
        return Result.success(majorTableService.findByMajor(major));
    }

    //查询表中所有的专业名称
    @GetMapping("/findMajors")
    public Result<List> findMajors(){
        return Result.success(majorTableService.findMajors());
    }

    //上传pdf文件
    @PostMapping("/uploadPDF")
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id) {

        String contentType = file.getContentType();
        if (contentType == null) {
            return Result.error("520", "文件不能为空");
        }
        if (!contentType.equals("application/pdf")) {
            return Result.error("520", "只能上传pdf文件");
        }
        try {
            String filePath = new File("").getCanonicalPath();

            MajorTable majorTable = majorTableService.findById(id);

            String filename = majorTable.getMajor() + "-" + majorTable.getMajorVersion() + "版.pdf";

            String filePath_ = filePath + "/pdf/" + majorTable.getMajor();
            File fileRealPath = new File(filePath_);
            if (!fileRealPath.exists()) {
                if (!fileRealPath.mkdirs()) {
                    return Result.error("520", "文件夹创建失败");
                }
            }

            File result = new File(filePath_ + "/" + filename);
            file.transferTo(result);
            majorTable.setIsLoad(1);
            majorTableService.save(majorTable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }


    //展示pdf文件
    @GetMapping("/showPDF/{major}/{majorVersion}")
    public ResponseEntity<byte[]> show(@PathVariable String major,@PathVariable String majorVersion) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String filePath = new File("").getCanonicalPath();

        String filename2 = major + "-" + majorVersion + "版.pdf";

        Path path = Paths.get(filePath + "/pdf/" + major + "/" + filename2);
        File file = path.toFile();
        if (!file.exists()) {
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(null, headers, HttpStatus.OK);
        }
        byte[] bytes = Files.readAllBytes(path);
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    //pdf文件下载
    @GetMapping("/download/{major}/{majorVersion}")
    public Result download(@PathVariable String major,@PathVariable String majorVersion, HttpServletResponse response) throws IOException {

        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(major+"-"+majorVersion+"版.pdf","UTF-8"));

        String filePath = new File("").getCanonicalPath()+"/pdf/"+major+"/"+major+"-"+majorVersion+"版.pdf";
        if(!FileUtil.exist(filePath)){
            return Result.error("520","文件下载失败");
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
        return Result.success();
    }
}
