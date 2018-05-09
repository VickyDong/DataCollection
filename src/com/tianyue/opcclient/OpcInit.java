package com.tianyue.opcclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * opcInit�����ڳ�ʼ��OPC�ͻ��ˡ�
 * ÿ��OpcClientAPIʵ����Ӧĳһ��server-group���
 * �����Ҫ���ʶ��server��group����new���opcInitʵ�����ֱ�����
 * �乹�캯������Ϊ�����ļ��ĵ�ַ������ʹ�þ���·������
 * readConfig����ʵ�ֶ������ļ��Ķ�ȡ�ͽ�����
 * ������������Ϣ�洢�ڶ����ڲ����ڽ���OPC���ӵ�ʱ����á�
 * ArrayList<opcItem> itemList �����ڽ���OPC����ǰ�洢item�б�
 * @author tian
 */

public class OpcInit {
	private File file;
	private Scanner input;
	private String s, s1, s2,s3,s4; //���ڶ�ȡ�����ļ�ʱ���ַ����������
	public String IPAddress;		//OPC������IP��ַ
	public String serverName;		//OPC��������
	public String groupName;		//OPC������������һ��ΪGroup0��
	public ArrayList<OpcItem> itemList;
	public int itemCnt;				//ͳ�Ƶ�ǰ��ӵı�������
	public int connectionId;   		//���ֶ��OPC����������
									//һ��OpcInit����ֻ��Ӧһ��Opc����������
									//MainControlPanel�е�basicAddress��OpcInit�е�connetionId���õ�ַ
									//Ӧ��ӦH2���е�DEVICE_ID�ֶ�
	/**
	 * ���캯��
	 * @param configFileName
	 * �乹�캯������Ϊ�����ļ��ĵ�ַ������ʹ�þ���·����
	 */
	public OpcInit(String configFileName, int connectionId) {
		file = new File(configFileName);
		input = null;
		itemList = new ArrayList<OpcItem>();
		itemCnt = 0;
		this.connectionId = connectionId;
	}
	/**
	 * �������õ�ʵ��
	 * @return
	 * �ļ�δ�ҵ��򷵻�-1����������򷵻�0
	 */
	public int readConfig() {
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		while (input.hasNext()) {
			s = input.nextLine();
			if (s.startsWith("//")) //ע����
				continue;
			s1 = s.substring(0, s.indexOf('='));
			s2 = s.substring(s.indexOf('=') + 1, s.length());
			if (s1.equals("IP"))
			    IPAddress = s2.trim();
			else 
			if (s1.equals("ServerName"))
				serverName = s2.trim();
			else
			if (s1.equals("GroupName"))
				groupName = s2.trim();
			else{   //����������
				s3 = s2.substring(s2.indexOf('=') + 1, s2.length()).trim();
				s2 = s2.substring(0, s2.indexOf('=')).trim(); //������
				s4 = s3.substring(s3.indexOf('=') + 1, s3.length()).trim();//��д����
				s3 = s3.substring(0, s3.indexOf('=')).trim(); //��������
				System.out.println(s1+" "+s2+" "+s3+" "+s4);
				OpcItem item = new OpcItem();
				item.name = s2;
				
				if (s3.equals("float"))
					item.valueType = 4;
				else
				if (s3.equals("double"))
					item.valueType = 5;
				else
				if (s3.equals("int"))
					item.valueType = 22;
				else
				if (s3.equals("bool"))
					item.valueType = 11;

				item.address = Integer.toHexString(itemCnt); 
				
				item.readable = (s4.startsWith("R"))?true:false;
				item.writable = (s4.endsWith("W"))?true:false;
				
				itemList.add(item);
				itemCnt++;		    	
			}
		}
		return 0;
	}
	
	public String getItemNameByAddress(String address){
		Iterator<OpcItem> it = itemList.iterator();
		OpcItem oi;
		while (it.hasNext())
		{
			oi = it.next();
			if (oi.address.equals(address))
				return oi.name;
		}
		return "null";
	}
	
	public int getItemIndexByName(String itemName){
		Iterator<OpcItem> it = itemList.iterator();
		OpcItem oi;
		int cnt=0;
		while (it.hasNext())
		{
			oi = it.next();
			if (oi.name.equals(itemName))
				return cnt;
			cnt++;
		}
		return -1;
	}

	public static void main(String args[]) {
		//����ʵ�������캯������Ϊ�����ļ���ַ������ʹ�þ���·����
		OpcInit init = new OpcInit("D:/OpcConfig.ini",0);
		if (init.readConfig()==0){
			OpcClientAPI opc = new OpcClientAPI();
			System.out.println(opc.connect(init.IPAddress, init.serverName, init.groupName));
			Iterator<OpcItem> it = init.itemList.iterator();
			while (it.hasNext())
			{
				OpcItem oi = (OpcItem) it.next();
				System.out.println(oi.name);
				opc.addItem(init.connectionId, oi.name, oi.valueType, true);
			}
			while (true){
				OpcItem oi;
				it = init.itemList.iterator();
				//һ�ζ�ȡ����
				if (it.hasNext()){
				    opc.readFloatSync(init.connectionId, ((OpcItem)it.next()).name);
				}
				// ֮��ֱ�ӷ�����Ӧ�洢��Ԫ�������
				it = init.itemList.iterator();
				while (it.hasNext())
				{
					oi = (OpcItem) it.next();
					System.out.println(opc.getFloatData(init.connectionId, oi.name));
				}
	
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
			
	}
}