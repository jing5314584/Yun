package com.yun.controller;

import com.vdurmont.emoji.EmojiParser;
import com.vdurmont.emoji.EmojiParser.FitzpatrickAction;

public class Test {
	public static void main(String[] args) {
//		String str = "Here is a boy: \uD83D\uDC66\uD83C\uDFFF!";
//		System.out.println(EmojiParser.parseToAliases(str));
/*		String str = "An 😀awesome 😃string with a few 😉emojis!";
		String result = EmojiParser.parseToAliases(str);
		System.out.println(result);*/
//		String str = "Here is a boy: \uD83D\uDC66\uD83C\uDFFF!";
		String str = "&#127471;&#127467;";
//		String str = "An 😀awesome 😃string with a few 😉emojis!";

//		String resultDecimal = EmojiParser.parseToHtmlDecimal(str);
//		System.out.println("resultDecimal=:"+resultDecimal);
		
		String result = EmojiParser.parseToUnicode(str);
		System.out.println("resultDecimal=:"+result);
	}
}
