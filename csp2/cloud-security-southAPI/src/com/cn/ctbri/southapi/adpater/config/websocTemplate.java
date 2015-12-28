package com.cn.ctbri.southapi.adpater.config;

public class websocTemplate {
	private String template1 ="{\"dns\":{\"enabled\":true},\"dns_hijack\":{\"enabled\":true},\"ping\":{\"enabled\":true},\"http_get\":{\"enabled\":true},\"http_get_full_time\":{\"enabled\":true},\"black_links\":{\"enabled\":false},\"malscan\":{\"enabled\":false},\"keyword\":{\"enabled\":false},\"sql\":{\"enabled\":false},\"xss\":{\"enabled\":false},\"webvul\":{\"enabled\":false},\"info_leak\":{\"enabled\":false,\"items\":[\"email\"]},\"cgi\":{\"enabled\":false},\"csrf\":{\"enabled\":false},\"form_crack\":{\"enabled\":false}}";

	public String getTemplate1() {
		return template1;
	}
	
}
