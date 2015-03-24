package com.hzy.artical.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {
	
	private static Util util = new Util();

	ConnectivityManager connectivity;
	NetworkInfo wifiNetworkInfo, mobileNetworkInfo;
	
	public static Util getUtil(){
		return util;
	}

	public boolean checkNetworkState(Context ctx) {
		connectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		wifiNetworkInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		mobileNetworkInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(!wifiNetworkInfo.isConnected() && !mobileNetworkInfo.isConnected()){
			return false;
		}
		
		return true;
	}
}
	
//	import org.htmlparser.Parser;
//	import org.htmlparser.filters.NodeClassFilter;
//	import org.htmlparser.filters.OrFilter;
//	import org.htmlparser.nodes.TextNode;
//	import org.htmlparser.tags.LinkTag;
//	import org.htmlparser.util.NodeList;
//	import org.htmlparser.util.ParserException;
//	import org.htmlparser.NodeFilter;
//	import org.htmlparser.Node;
//	import java.io.BufferedReader;
//	import java.io.InputStreamReader;
//	import java.io.FileInputStream;
//	import java.io.File;
//	 
//	class Test
//	{
//		private static String ENCODE = "GB2312";//定义编码字符集
//		/**
//		 * *@author nobody
//		 * @param szFileName
//		 * @return
//		 */
//	    public static String readFile( String szFileName )//文件读入函数
//	    {
//	        try {
//	            BufferedReader bis = new BufferedReader(new InputStreamReader(new FileInputStream( new File(szFileName)), ENCODE) );
//	            String szContent="";
//	            String szTemp;
//	            
//	            while ( (szTemp = bis.readLine()) != null) {
//	                szContent+=szTemp+"/n";
//	            }
//	            bis.close();
//	            return szContent;
//	        }
//	        catch( Exception e ) {
//	        	System.out.println("error2");
//	            return "";
//	        }
//	    }
//	    /**
//	     * 
//	     * @param tag
//	     * @param content
//	     * @return
//	     */
//	    public static StringBuffer delTag(String tag,StringBuffer content)//去除特定标签内容
//	    {
//	        String beginTag="<"+tag;
//	        String endTag="</"+tag+">";
//	        int pos1=0;
//	        int pos2=0;
//	        while((pos2=content.indexOf(beginTag,0))!=-1)
//	        {
//	        		pos1=content.indexOf(endTag,pos2)+endTag.length()-1;
//	        		if(pos1>pos2)
//	        		{
//	        			content=content.delete(pos2, pos1);
//	        		}
//	        		else
//	        		{
//	        			pos1=content.lastIndexOf("</");
//	        			if(pos1>pos2)
//	        			{
//	        				content=content.delete(pos2, pos1);
//	        				content=content.append(tag+"></body></html");
//	        			}
//	        			else
//	        			{
//	        				content=content.delete(pos2, content.length());
//	        				content=content.append("</body></html");
//	        			}
//	        		}
//	        }
//	        return content;
//	     }
//	    /**
//	     * 
//	     * @param str
//	     * @param filecontent
//	     * @return
//	     * @throws ParserException
//	     */
//	    public static StringBuffer readhtml(StringBuffer str,String filecontent) throws ParserException
//	    {//对html进行去噪.
//	    	StringBuffer sb=new StringBuffer(filecontent);
//	    	sb=delTag("script",sb);//去除标签script的内容
//	    	filecontent=sb.toString();
//	    	Parser parser=Parser.createParser(filecontent,ENCODE);
//	   	     NodeFilter textFilter=(NodeFilter) new NodeClassFilter(TextNode.class);
//			 NodeFilter linkFilter=(NodeFilter) new NodeClassFilter(LinkTag.class);
//			 int beforeLen=0;
//			 NodeList nodeList=null;
//			 OrFilter lastFilter=new OrFilter();
//			 lastFilter.setPredicates(new NodeFilter[]{textFilter,linkFilter});
//			 nodeList=parser.parse(lastFilter);
//			 Node[] nodes=nodeList.toNodeArray();
//			 for(int i=0;i<nodes.length;++i)
//			 {
//				 Node anode=nodes[i];
//				 String textLine="";
//				 if(anode instanceof TextNode)
//				 {
//					 TextNode textnode=(TextNode)anode;
//					 textLine=textnode.toPlainTextString().trim();
//					 textLine=textLine.replace("p;", "");//去除特定文本
//					 textLine=textLine.replace(">", "");
//					 textLine=textLine.replace("<", "");
//					 textLine=textLine.replace("&nbsp;", "");
//					 textLine=textLine.replace("&nbs", "");
//					 textLine=textLine.replace(""", "");
//					 if(textLine.compareTo("不支持flasg")==0)
//						 continue;
//					 if(textLine.indexOf("©")!=-1||textLine.indexOf("copyright")!=-1)
//					 {
//						 if(i>nodes.length-10)
//							 break;
//					 }
//					 if(textLine.length()>3)
//					 {
//						 if(beforeLen==0||(beforeLen!=0&&(beforeLen/textLine.length()<4)))
//						 {
//							 str.append(textLine+"/n");
//						 }
//						 beforeLen=textLine.length();
//					 }
//				 }
//				 else if(anode instanceof LinkTag)
//				 {
//					 if(i<nodes.length-1)
//						 i++;
//				 }
//			 }
//	    	return str;
//	    }
//	  /**
//	   * 
//	   * @param args
//	   * @throws Exception
//	   */
//	    public static void main(String[] args) throws Exception
//	    {
//	    	 StringBuffer mainContent=new StringBuffer();
//	    	 String szContent =readFile("E://test");
//	    	 System.out.println(readhtml(mainContent,szContent));
//	    }
//	 }
//
//}
