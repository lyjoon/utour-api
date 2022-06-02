package com.utour.util;

import com.utour.TestLocalApplication;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

public class TestStringUtil extends TestLocalApplication {

    @Test
    public void testMarkdown(){
        String content = "ddddddddddddddddddddddddddddddddddddddddddddd\n" +
                "\n" +
                "eeeeeeeeeeeeeee\n"+
                "12333 4\n"+
                "![LVPS (1) (2).jpg](/api/v1/image/temp/20220530-38fbb701-8a81-42c0-a41f-18ce16ee6e6b.jpg)![PBV (2) (2).jpg](/api/v1/image/content/20220603$1de7c4d0-65f3-41c0-86a5-3b8737cee4c8.jpg) sdf \n --:P";

        log.info("{}", StringUtils.findMarkdownImageFileName(content).stream().collect(Collectors.joining(",")));
    }

    @Test
    public void testMarkdown2(){
        String content = "ddddddddddddddddddddddddddddddddddddddddddddd\n" +
                "\n" +
                "eeeeeeeeeeeeeee\n"+
                "12333 4\n"+
                "![LVPS.jpg](/api/v1/image/temp/20220530-38fbb701-8a81-42c0-a41f-18ce16ee6e6b.jpg)![PBV.jpg](/api/v1/image/content/20220603$1de7c4d0-65f3-41c0-86a5-3b8737cee4c8.jpg) sdf \n --:P";
        java.util.List<String> results = StringUtils.findMarkdownImages(content);
        log.info("{}", results.size());
    }
}
