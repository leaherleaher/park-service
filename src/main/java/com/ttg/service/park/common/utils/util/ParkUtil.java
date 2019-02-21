package com.ttg.service.park.common.utils.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

/**
 * <p>Title: ParkUtil</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/7 17:21
 */
public class ParkUtil {

    /**
     * @Description //TODO XML转MAP
     * @Param [strXML]
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }

    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }

    /**
     * 生成调停车场系统接口的参数Key
     * Key的生成步骤
     * 1.参数拼接成String
     * 2.再加上日期（年月日)和app密钥生成新的NewString
     * 3.对NewString进行MD5 32加密
     * @return
     */
    public static String createKey(LinkedHashMap<String, String> map, String appSecret){

        //Set<Map.Entry<String, String>> es =  params.entrySet();
        //Iterator<Map.Entry<String,String>> it =  es.iterator();

        //生成 stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA";
        /*while (it.hasNext()){
            Map.Entry<String,String> entry = (Map.Entry<String,String>)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(null != v && !"".equals(v) && !"key".equals(k) && !"appSecret".equals(k)){
                sb.append(k+"="+v+"&");
            }
        }
        //拼接日期->年月日
        sb.append("date=").append(DateUtil.format(new Date()));
        //拼接appSecret 由接口方提供
        sb.append("appSecret=").append(appSecret);*/

        StringBuilder sb = new StringBuilder();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            /*Map.Entry<String,String> entry = (Map.Entry<String,String>)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(null != v && !"".equals(v) && !"key".equals(k) && !"appSecret".equals(k)){
                //sb.append(k+"="+v+"&");
                sb.append(v);
            }*/
            Map.Entry<String,String> entry = (Map.Entry) iter.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if(null != v && !"".equals(v) && !"key".equals(k) && !"appSecret".equals(k)) {
                //sb.append(k+"="+v+"&");
                sb.append(v);
            }
        }
        //拼接日期->年月日
        sb.append(DateUtil.format(new Date()));
        //拼接appSecret 由接口方提供
        sb.append(appSecret);
        //生成md5加密
        String key = CommonUtils.MD5(sb.toString());
        return key;
    }

}
