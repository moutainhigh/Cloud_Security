package com.cn.ctbri.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * 一个多线程支持断点续传的工具类<br/>
 */
public class HttpDownloader {
	/**
	 * 
	 * @param is
	 * @param l
	 *            服务器文件的大小
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	public static Response download(File file, Long l,
			HttpServletRequest request) throws IOException {
		InputStream is = new java.io.FileInputStream(file);
		
		long p = 0;
		ResponseBuilder rb = Response.ok().header("Accept-Ranges", "bytes");
		// 如果是第一次下,还没有断点续传,状态是默认的 200,无需显式设置
		// 响应的格式是:
		// HTTP/1.1 200 OK
		if (request.getHeader("Range") != null) // 客户端请求的下载的文件块的开始字节
		{
			// 如果是下载文件的范围而不是全部,向客户端声明支持并开始文件块下载
			// 要设置状态
			// 响应的格式是:
			// HTTP/1.1 206 Partial Content
			rb.status(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);

			// 从请求中得到开始的字节
			// 请求的格式是:
			// Range: bytes=[文件块的开始字节]-
			System.out.println(request.getHeader("Range"));
			p = Long.parseLong(request.getHeader("Range").replaceAll("bytes=",
					"").split("-")[0]);
		}
		// 下载的文件(或块)长度
		// 响应的格式是:
		// Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
		rb.header("Content-Length", new Long(l - p).toString());
		if (p != 0) {
			// 不是从最开始下载,
			// 响应的格式是:
			// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
			rb.header("Content-Range", "bytes " + new Long(p).toString()
							+ "-" + new Long(l - 1).toString() + "/"
							+ new Long(l).toString());
		}
		// 使客户端直接下载
		// 响应的格式是:
		// Content-Type: application/octet-stream
		rb.type(MediaType.APPLICATION_OCTET_STREAM_TYPE);
		try {
			is.skip(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		rb.entity(is);
		rb.header("Content-Disposition", "attachment;filename=" + file.getName());
		return rb.build();
	}

}
