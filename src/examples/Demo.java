package examples;

import java.util.Date;

import com.anysoft.formula.DateUtil;
import com.anysoft.formula.DefaultFunctionHelper;
import com.anysoft.formula.Expression;
import com.anysoft.formula.Function;
import com.anysoft.formula.Parser;
import com.anysoft.formula.DataProvider;
import com.anysoft.formula.FunctionHelper;

public class Demo {

	public static void simple(String[] args) {
		Parser parser = new Parser();
		
		String formula = "1>2 || 3<5 && 2";
		
		Expression expr = parser.parse(formula);
		
		System.out.println(expr.toString());
		System.out.println(expr.getValue(null));
	}

	public static void withDataProvider(String[]args){
		Parser parser = new Parser();
		
		String formula = "1+2+to_long(id)";
		
		Expression expr = parser.parse(formula);
		
		System.out.println(expr.toString());
		System.out.println(expr.getValue(new DataProvider(){
			@Override
			public String getValue(String varName, Object context,String defaultValue) {
				if (varName.equals("id")){
					return "100";
				}
				return null;
			}
			@Override
			public Object getContext(String varName) {
				return new Object();
			}
		}));		
	}
	
	public static void withDefaultFunctionHelper(String[]args){
		DefaultFunctionHelper functionHelper = new DefaultFunctionHelper();
		Parser parser = new Parser(functionHelper);
		String formula = "1+2+choice(2>1,100,2000)";
		Expression expr = parser.parse(formula);
		
		System.out.println(expr.toString());
		System.out.println(expr.getValue(null));			
	}
	
	public static void withFunctionHelper(String[]args){

        Parser parser = new Parser((funcName) -> {
            if (funcName.equals("choice")){
                return new Function.Choice();
            }
            return null;
        } );

        String formula = "1+2+choice(2>1,100,2000)";
		
		Expression expr = parser.parse(formula);
		
		System.out.println(expr.toString());
		System.out.println(expr.getValue(null));		
	}	
	
	public static void testNvl(String [] args){
		DefaultFunctionHelper functionHelper = new DefaultFunctionHelper();
		Parser parser = new Parser(functionHelper);
		String formula = "nvl(null_var,1000) + to_long(nvl(id,'20'))";
		Expression expr = parser.parse(formula);
		
		System.out.println(expr.toString());
		System.out.println(expr.getValue(new DataProvider(){
			@Override
			public String getValue(String varName, Object context,String defaultValue) {
				if (varName.equals("id")){
					return "100";
				}
				return null;
			}
			@Override
			public Object getContext(String varName) {
				return new Object();
			}
		}));			
	}
	public static void testDate(String [] args){
		DefaultFunctionHelper functionHelper = new DefaultFunctionHelper();
		Parser parser = new Parser(functionHelper);
		String formula = "to_char(to_date(now,'yyyyMMdd'),'yyyyMMddhhmmss')";
		Expression expr = parser.parse(formula);
		
		System.out.println(expr.toString());
		System.out.println(expr.getValue(new DataProvider(){
			@Override
			public String getValue(String varName, Object context,String defaultValue) {
				if (varName.equals("now")){
					return DateUtil.formatDate(new Date(), "yyyyMMdd");
				}
				return null;
			}
			@Override
			public Object getContext(String varName) {
				return new Object();
			}
		}));			
	}	
	
	public static void testString(String [] args){
		DefaultFunctionHelper functionHelper = new DefaultFunctionHelper();
		Parser parser = new Parser(functionHelper);
		String formula = "substr(hello,instr(hello,'world'),9)+match(hello,'Hello*')+strlen(hello)";
		Expression expr = parser.parse(formula);
		
		System.out.println(expr.toString());
		System.out.println(expr.getValue(new DataProvider(){
			@Override
			public String getValue(String varName, Object context,String defaultValue) {
				if (varName.equals("hello")){
					return "Hello world";
				}
				return null;
			}
			@Override
			public Object getContext(String varName) {
				return new Object();
			}
		}));			
	}	
	
	public static void main(String[]args){
		testDate(args);
		testString(args);
		testNvl(args);
		simple(args);
		// withDataProvider(args);
        withDefaultFunctionHelper(args);
        withFunctionHelper(args);
	}	
}
