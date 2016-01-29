package divide_word;

import java.util.ArrayList;
import java.util.List;

import edu.fudan.ml.types.Dictionary;
import edu.fudan.nlp.cn.tag.CWSTagger;
import edu.fudan.nlp.corpus.StopWords;

/**
 * FudanNLP 使用示例
 * @author hty
 *
 */
public class divide_word {
	/**
	 * 主程序
	 * @param args 
	 * @throws IOException 
	 * @throws  
	 */
	public static void main(String[] args) throws Exception {
		CWSTagger tag = new CWSTagger("./models/seg.m");
		System.out.println("不使用词典的分词");
		String str = "媒体计算研究所成立了，高级数据挖掘（data mining）很难。乐phone热卖！";
		String s = tag.tag(str);
		System.out.println(s);
		
		//设置英文预处理
		tag.setEnFilter(true);
		s = tag.tag(str);
		System.out.println(s);
//		tag.setEnFilter(false);
		System.out.println("\n设置临时词典：");
		ArrayList<String> al = new ArrayList<String>();
		al.add("数据挖掘");
		al.add("媒体计算研究所");
		al.add("乐phone");
		Dictionary dict = new Dictionary(false);
		dict.addSegDict(al);
		tag.setDictionary(dict);
		s = tag.tag(str);
		System.out.println(s);
		
		
		CWSTagger tag2 = new CWSTagger("./models/seg.m", new Dictionary("./models/dict.txt"));
		System.out.println("\n使用词典的分词");
		String str2 = "媒体计算研究所成立了，高级数据挖掘（data mining）很难。乐phone热卖！";
		String s2 = tag2.tag(str2);
		System.out.println(s2);
		
		//使用不严格的词典
		CWSTagger tag3 = new CWSTagger("./models/seg.m", new Dictionary("./models/dict_ambiguity.txt",true));
		// 尽量满足词典，比如词典中有“成立”， “成立了”和“了”，会使用Viterbi决定更合理的输出
		System.out.println("\n使用不严格的词典的分词：");
		String str3 = "媒体计算研究所成立了，高级数据挖掘（data mining）很难。乐phone热卖！";
		String s3 = tag3.tag(str3);
		System.out.println(s3);
		str3 = "我送给力学系的同学一个玩具 （送给给力力学力学系都在词典中）";
		s3 = tag3.tag(str3);
		System.out.println(s3);
		StopWords stopWords = new StopWords("./models/stopwords/StopWords.txt");
		String[] words = s3.split("\\s");
		List<String> baseWords;
		baseWords = stopWords.phraseDel(words);
		String nonStopWordsS3 = listToString(baseWords);
		System.out.println("\n去除停词后：");
		System.out.println(nonStopWordsS3);
		
		
	//	System.out.println("\n处理文件：");
	//	String s4 = tag.tagFile("./example-data/data-tag.txt");
	//	System.out.println(s4);
		
	//	String s5 = tag2.tagFile("./example-data/data-tag.txt");
	//	System.out.println(s5);
		
	}
	
	private static String listToString(List<String> stringList){
        /**
         *  turning a list to a string
         */
		 if (stringList==null) {
		     return null;
		 }
		 StringBuilder result=new StringBuilder();
		 
		 boolean flag=false;
		 
		 for (String string : stringList) {
		     if (flag) {
		         result.append(" ");
		     }
		     else {
		         flag=true;
		     }
		     result.append(string);
		 }
		 return result.toString();
	}

}
