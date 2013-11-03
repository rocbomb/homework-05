
import java.io.*;
import jxl.*;
import jxl.write.*;


public class Excel {
	File fl;
	Workbook book;
	Workbook bookw;
	int userNumber=0;
	Sheet sheet;
	public Excel(File fl){
		this.fl = fl;
		try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.userNumber = setNumber();

	}
	
	public int setNumber(){
		int i = 0;
		try {
			book = Workbook.getWorkbook(fl);
			Sheet sheet=book.getSheet(0);
			while(true){
				Cell cell1=sheet.getCell(0,i);
				String result=cell1.getContents();
				//System.out.println(result);
				i++;
			}
		} catch (Exception  e) {
			return i;
		}
	}
	
	public int getUserNum(){
		return userNumber;
	}
	
	
	public String getUserPassword(String name){
		try {
			book = Workbook.getWorkbook(fl);
			Sheet sheet=book.getSheet(0);
			for(int i=0; i<userNumber; i++)
			{
				Cell cell1=sheet.getCell(0,i);
				String result=cell1.getContents();
				//System.out.println(result);
				if(result.equals(name))
				{
					Cell cell2=sheet.getCell(1,i);
					return cell2.getContents();
					
				}
			}
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "false";

	}
	
	public boolean addNewUser(String name,String password){
		try {
			book = Workbook.getWorkbook(fl);
			Sheet sheet=book.getSheet(0);
			int i;
			for(i=0; i<userNumber; i++)
			{
				Cell cell1=sheet.getCell(0,i);
				String result=cell1.getContents();
				//System.out.println(result);
				if(result.equals(name))
					break;
			}
			if(i < userNumber)
				return false;
			
			WritableWorkbook bookw = Workbook.createWorkbook(fl,book);
			WritableSheet  sheet2 = bookw.getSheet(0);
            Label label1  = new  Label( 0,userNumber,name);
            Label label2  = new  Label( 1,userNumber,password);
            sheet2.addCell(label1);
            sheet2.addCell(label2);
            bookw.write();
            bookw.close();
            userNumber++;
            } catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void removeNewUser(String name){
		try {
			book = Workbook.getWorkbook(fl);
			WritableWorkbook bookw = Workbook.createWorkbook(fl,book);
			WritableSheet  sheet2 = bookw.getSheet(0);
			Sheet sheet=book.getSheet(0);
			for(int i=0; i<userNumber; i++)
			{
				Cell cell1=sheet.getCell(0,i);
				String result=cell1.getContents();
				//System.out.println(result);
				if(result.equals(name))
				{
					sheet2.removeRow(i);
					break;
				}
			}
            bookw.write();
            bookw.close();
            userNumber--;
            } catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getAll(){
		String msg = "LIST";
		try {
			book = Workbook.getWorkbook(fl);
			Sheet sheet=book.getSheet(0);
			for(int i=0; i<userNumber; i++)
			{
				Cell cell=sheet.getCell(0,i);
				String name=cell.getContents();
				msg = msg +":"+ name;
			}
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

}
