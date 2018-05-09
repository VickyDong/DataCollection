package com.tianyue.opcclient;


/**
 * OpcClientAPIʵ��OPCͨ����ع���
 * ÿ��OpcClientAPIʵ����Ӧĳһ��server-group���
 * �����Ҫ���ʶ��server��group����new���opcInitʵ�����ֱ����ã���new���OpcClientAPIʵ���ֱ���
 * 
 * @author tian
 *
 */
public class OpcClientAPI {
	
	public int connectionId;
	
	static {
		System.loadLibrary("opc");
	}
	/**
	 * ����OPC����
	 * @param IPAddress
	 * OPC������IP��ַ
	 * @param serverName
	 * OPC��������
	 * @param groupName
	 * OPC������������һ�������Ϊgroup0
	 * @return
	 */
	public native int connect(String IPAddress, String serverName,
			String groupName);
	/**
	 * ���ط��������OPC����
	 * @param connectionId
	 * ������������OPC������������
	 * @param itemName
	 * OPC������
	 * @param itemType
	 * ��������
	 * @param isActive
	 * �Ƿ����˴�Ӧ���Ϊ�(true)
	 * @return
	 */
	public native int addItem(int connectionId, String itemName, int itemType, boolean isActive);
    //����read...Sync����Ϊ�ײ�ʵ�ֶ�ȡ�ķ������Ѿ��ڶ���ṹ���ٴΰ�װ������Ҫֱ�ӵ������·���
	public native float readFloatSync(int connectionId, String itemName);
	public native double readDoubleSync(int connectionId, String itemName);
	public native int readIntSync(int connectionId, String itemName);
	public native boolean readBoolSync(int connectionId, String itemName);
	public native String readStringSync(int connectionId, String itemName);
	//����get...Data����Ϊ�ӵײ�ṹ�ж�ȡ���ݵķ������Ѿ��ڶ���ṹ���ٴΰ�װ������Ҫֱ�ӵ������·���
	public native float getFloatData(int connectionId, String itemName);
	public native double getDoubleData(int connectionId, String itemName);
	public native int getIntData(int connectionId, String itemName);
	public native boolean getBoolData(int connectionId, String itemName);
	public native String getStringData(int connectionId, String itemName);
	public native String getTimeStamp(int connectionId, String itemName);
	//����write...Sync����Ϊ���õײ�д���ݵ��������ķ���
	public native int writeFloatSync(int connectionId, String itemName, float value);
	public native int writeDoubleSync(int connectionId, String itemName, double value);
	public native int writeIntSync(int connectionId, String itemName, int value);
	public native int writeBoolSync(int connectionId, String itemName, boolean value);
	public native int writeStringSync(int connectionId, String itemName, String value);
	
	public native void onReadComplete(float f);
	public native void shutdownServer(int connectionId);
	
	/**
	 * ��������Ҫ��ˢ���������ݣ����ڵײ������޸ģ�
	 * read...Sync�������ص��ǲ�����ָ���ı�������ֵ
	 * ��ʵ���ϸñ������ڵķ������µ��������ݶ��Ѿ����µ��˵ײ�����ݽṹ����
	 * ֮��ֻ��Ҫ����get...Data����ȡ������
	 * ��������ȫ��ˢ�µ��ײ��refreshAll�����Ŀ����ɶ�ȡһ��OPC�����ķ���ʵ��
	 * @param connectionId
	 * ������������OPC������������
	 * @param firstItemName
	 * ��֮ǰ���˳��ĵ�һ��������
	 * @return
	 * �ɹ��򷵻ص�һ��������ֵ
	 * ʧ�ܷ���-1
	 */
	public float refreshAll(int connectionId, String firstItemName){
		return readFloatSync(connectionId, firstItemName);
	}
	/**
	 * ������
	 * @param args
	 */
	public static void main(String args[]) {
		
		OpcClientAPI opc = new OpcClientAPI();
		System.out.println(opc.connect("127.0.0.1",
				"Matrikon.OPC.Simulation.1", "Group0"));
		opc.addItem(0, "testVar", 4, true);
		opc.writeFloatSync(0,"testVar", (float)345.3);
		//System.out.println(opc.addItem(0, "Random.Int2", 4, true));
		// System.out.println(opc.formItemList());
		//System.out.println(opc.readStringSync(0,"Bucket Brigade.String"));
		while (true){
			System.out.println("--------------------------------");
			System.out.println(opc.readFloatSync(0, "Random.Int1"));
			/*
			opc.refreshAll(0, "AP.ap0");
			for (int i=0;i<=200;i++){
				System.out.println("AP.ap"+String.valueOf(i));
				System.out.println(opc.getFloatData(0, "AP.ap"+String.valueOf(i)));
				//System.out.println(opc.readFloatSync(0, "AP.ap"+String.valueOf(i)));
			}
			*/
		    try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(opc.getTimeStamp(0,"Random.Int2"));
		//System.out.println(opc.writeFloatSync(0,"Bucket Brigade.Real4", (float)345.3));
		
	}
}