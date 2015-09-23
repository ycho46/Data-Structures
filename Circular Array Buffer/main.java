
public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

        CircularArrayBuffer<String> buff = new CircularArrayBuffer<String>();
        buff.setMode(BufferGrowMode.REGROW);
       	for(int i = 0; i<9;i++){
     //  		System.out.println(buff.size()+" front: " + buff.getFront() +
      // 				" rear " + buff.getRear() + " count " + buff.getCount() +
     //  				" length " + buff.getLength());
       		buff.add("A"+Integer.toString(i));
       		
       	}
       	buff.remove();
        buff.remove();
        buff.remove();
       	buff.add("A10");
        buff.add("A11");
        buff.add("A12");
        buff.add("A13");
        buff.add("A14");
//       	buff.add("A99");
//        buff.add("A100");
//        buff.add("A101");
//        buff.add("A102");
//        buff.add("A103");
       //	buff.print();
//       	for(int i = 0; i< 5; i++){
//       		buff.remove();
//       		System.out.print(buff.size()+"ff ");
//       	}
//        buff.remove();
//        buff.remove();
//        buff.remove();
//        buff.remove();
//        buff.remove();
//        System.out.println(buff.size());
	}

}
