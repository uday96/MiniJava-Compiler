//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJDepthFirst<R,A> implements GJVisitor<R,A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   
   public static HashMap<String,String> varhm = new HashMap<String,String>();
   public static HashMap<String,String> fnhm = new HashMap<String,String>();
   public static ArrayList<String> clsdec = new ArrayList<String>();
   public static HashMap<String,String> clextd = new HashMap<String,String>();
   public static Stack<String> currclass = new Stack<String>();
   public static Stack<String> scope = new Stack<String>();
   public static HashMap<String,String> cycExtd = new HashMap<String,String>();
   public static HashMap<String,String> fnparamhm = new HashMap<String,String>();
   //public static String fparams;
   //public static boolean assign;

   public void cycExtd(){
    for ( String key : clextd.keySet() ) {
        String s = "";
        String val = "";
        val = clextd.get(key);
        s = s+val;
        while(clextd.containsKey(val)){
          val = clextd.get(val);
          s = s+":"+val;
          if(s.contains(key)){
            ////System.out.println("Cyclic Inhert!");
            System.out.println("Type error");
            System.exit(0);
          }
        }
        cycExtd.put(key,s);
        ////System.out.println("Putting ("+key+","+s+")");
        
    }
  }

   public String printstack(){
      String sum="";
      String a="";
      for(int i=0; i< scope.size(); i++){
         a=scope.elementAt(i);
         sum = sum + a + ":"; 
       }
      return sum;  
    }

    public String getVarKey(String id){
    	String a = printstack() + id;
    	String b = currclass.peek() +":"+ id;
    	////System.out.println("Entered getVarKey with id: "+id);
    	for ( String key : varhm.keySet() ) {
    		if(key.equals(a)){
    			////System.out.println("returning: "+a);
    			return a;
    		}
		  }
  		for ( String key : varhm.keySet() ) {
      		if(key.equals(b)){
      			////System.out.println("returning: "+b);
      			return b;
      		}
  		}
      String cl = currclass.peek();
      
      if(cycExtd.containsKey(cl)){
        String ex = cycExtd.get(cl);
        for(String t : ex.split(":")){
          String aa = t+":"+id;
          ////System.out.println("Entered getFnKey : "+t+", "+id);
          for ( String key1 : varhm.keySet() ) {
            if(key1.equals(aa)){
              ////System.out.println("returning: "+aa);
              return aa;
            }
          }
        }  
      }
      
    		
		////System.out.println("Undeclared variable!");
		//System.exit(0);
		return "null";
    }

    public String getFnKey(String cl, String id){
    	String a = cl+":"+id;
    	////System.out.println("Entered getFnKey : "+cl+", "+id);
    	for ( String key : fnhm.keySet() ) {
    		if(key.equals(a)){
    			////System.out.println("returning: "+a);
    			return a;
    		}
      }
      String ex = cycExtd.get(cl);
      for(String t : ex.split(":")){
        String aa = t+":"+id;
        ////System.out.println("Entered getFnKey : "+t+", "+id);
        for ( String key1 : fnhm.keySet() ) {
          if(key1.equals(aa)){
            ////System.out.println("returning: "+aa);
            return aa;
          }
        }
		  }
		//System.out.println("Undeclared fn name!");
		//System.exit(0);
		return "null";
    }

   public GJDepthFirst(Maps map){
    super();
    this.varhm = map.AssVar();
    this.fnhm = map.AssFn();
    this.clsdec = map.AssCls();
    this.clextd = map.AssClExtd();
    this.fnparamhm = map.AssFnparam();
    //this.assign = false;
    cycExtd();
    //fparams = "";
    //varkeys = new ArrayList<String>(varhm.keySet());
    //fnkeys = new ArrayList<String>(fnhm.keySet());
   }

   public R visit(NodeList n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n, A argu) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n, A argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public R visit(NodeSequence n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n, A argu) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public R visit(Goal n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> PrintStatement()
    * f15 -> "}"
    * f16 -> "}"
    */
   public R visit(MainClass n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      //n.f1.accept(this, argu);
      String cl = n.f1.f0.toString();
      currclass.push(cl);
      scope.push(cl);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      scope.push("main");
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      //n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, argu);
      n.f15.accept(this, argu);
      scope.pop();
      n.f16.accept(this, argu);
      currclass.pop();
      scope.pop();
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public R visit(TypeDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public R visit(ClassDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      //n.f1.accept(this, argu);
      String cl = n.f1.f0.toString();
      currclass.push(cl);
      scope.push(cl);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      currclass.pop();
      scope.pop();
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   public R visit(ClassExtendsDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      //n.f1.accept(this, argu);
      String cl = n.f1.f0.toString();
      currclass.push(cl);
      scope.push(cl);
      n.f2.accept(this, argu);
      //n.f3.accept(this, argu);
      String b = n.f3.f0.toString();
      if(!clsdec.contains(b)){
      	System.out.println("Type error");
      	System.exit(0);
      }
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      currclass.pop();
      scope.pop();
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public R visit(VarDeclaration n, A argu) {
      R _ret=null;
      String t = n.f0.accept(this, argu).toString();
      if(!t.equals("int") && !t.equals("int[]") && !t.equals("boolean")){
        if(!clsdec.contains(t)){
          System.out.println("Type error");
          System.exit(0);
        }
      }
      //n.f1.accept(this, argu);
      //String a = n.f1.f0.toString();
      //String val = printstack() + a;
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   public R visit(MethodDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      
      String id,ty,rex,val;
      ty = n.f1.accept(this, argu).toString();
      //n.f2.accept(this, argu);

      if(!ty.equals("int") && !ty.equals("int[]") && !ty.equals("boolean")){
        if(!clsdec.contains(ty)){
          System.out.println("Type error");
          System.exit(0);
        }
      }

      id = n.f2.f0.toString();
      val = printstack() + id;
      scope.push(id);
      //fschm.put(id,val);
      //System.out.println("fschm putting "+id+" : "+val);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      rex = n.f10.accept(this, argu).toString();
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      scope.pop();
      //deletelater
      //System.out.println("Checking return type of "+val);
      if(clsdec.contains(ty) && clsdec.contains(rex) && !ty.equals(rex)){
        //rex can extend ty
        if(cycExtd.containsKey(rex)){
            String s = cycExtd.get(rex);
            if(!s.contains(ty)){
              System.out.println("Type error");
              System.exit(0);      
            }
        }
        else{
          System.out.println("Type error");
          System.exit(0);      
        }
        
      }
      else{
        if(!ty.equals(rex)){
          System.out.println("Type error");
          System.exit(0);
        }  
      }
      
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public R visit(FormalParameterList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public R visit(FormalParameter n, A argu) {
      R _ret=null;
      //n.f0.accept(this, argu);
      //n.f1.accept(this, argu);
      //String a,val;
      //a = n.f1.f0.toString();
      //val = printstack() + a;
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public R visit(FormalParameterRest n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public R visit(Type n, A argu) {
      R _ret=null;
      _ret = (R)n.f0.accept(this, argu).toString();
      return _ret;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public R visit(ArrayType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      _ret = (R)("int[]");
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public R visit(BooleanType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = (R)("boolean");
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public R visit(IntegerType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = (R)("int");
      return _ret;
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public R visit(Statement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public R visit(Block n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public R visit(AssignmentStatement n, A argu) {
      R _ret=null;
      String a,b;
      a = n.f0.accept(this, argu).toString();
      //a = varhm.get(schm.get(n.f0.f0.toString()));
      n.f1.accept(this, argu);
      b = n.f2.accept(this, argu).toString();
      n.f3.accept(this, argu);
      if(clsdec.contains(a) && clsdec.contains(b)){
        if(!a.equals(b)){
          if(cycExtd.containsKey(b)){
            String s = cycExtd.get(b);
            if(!s.contains(a)){
              System.out.println("Type error");
              System.exit(0);      
            }
          }
          else{
            System.out.println("Type error");
            System.exit(0);      
          }
        }
      }
      else{
        if(!a.equals(b)){
          System.out.println("Type error");
          System.exit(0);
        }
      }
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   //TODO
   public R visit(ArrayAssignmentStatement n, A argu) {
      R _ret=null;
      String a,b,c;
      //a = varhm.get(schm.get(n.f0.f0.toString()));
      a = n.f0.accept(this, argu).toString();
      n.f1.accept(this, argu);
      b = n.f2.accept(this, argu).toString();
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      c = n.f5.accept(this, argu).toString();
      n.f6.accept(this, argu);
      if(!a.equals("int[]") || !b.equals("int") || !c.equals("int")){
        System.out.println("Type error");
        System.exit(0);
      }
      return _ret;
   }

   /**
    * f0 -> IfthenElseStatement()
    *       | IfthenStatement()
    */
   public R visit(IfStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(IfthenStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String a = n.f2.accept(this, argu).toString();
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      if(!a.equals("boolean")){
        System.out.println("Type error");
        System.exit(0);
      }
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public R visit(IfthenElseStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String a = n.f2.accept(this, argu).toString();
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      if(!a.equals("boolean")){
        System.out.println("Type error");
        System.exit(0);
      }
      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(WhileStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String a = n.f2.accept(this, argu).toString();
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      if(!a.equals("boolean")){
        System.out.println("Type error");
        System.exit(0);
      }
      return _ret;
   }

   /**
    * f0 -> "//System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public R visit(PrintStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String a = n.f2.accept(this, argu).toString();
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      //System.out.println(a);
      if(!a.equals("int")){
        System.out.println("Type error");
        System.exit(0); 
      }
      return _ret;
   }

   /**
    * f0 -> OrExpression()
    *       | AndExpression()
    *       | CompareExpression()
    *       | neqExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | DivExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
   public R visit(Expression n, A argu) {
      R _ret=null;
      String a = n.f0.accept(this, argu).toString();
      //if(!a.equals("null"))
      _ret = (R)a;
      // if(assign){
      //   fparams+=a;  
      //   //System.out.println("------------------"+fparams);
      //   assign = false;
      // }
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public R visit(AndExpression n, A argu) {
      R _ret=null;
      String a,b;
      a = n.f0.accept(this, argu).toString();
      n.f1.accept(this, argu);
      b = n.f2.accept(this, argu).toString();
      if(!a.equals(b) || !a.equals("boolean") || !b.equals("boolean") ){
        System.out.println("Type error");
        System.exit(0);
      }
      _ret = (R)("boolean");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "||"
    * f2 -> PrimaryExpression()
    */
   public R visit(OrExpression n, A argu) {
      R _ret=null;
      String a,b;
      a = n.f0.accept(this, argu).toString();
      n.f1.accept(this, argu);
      b = n.f2.accept(this, argu).toString();
      if(!a.equals(b) || !a.equals("boolean") || !b.equals("boolean") ){
        System.out.println("Type error");
        System.exit(0);
      }
      _ret = (R)("boolean");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<="
    * f2 -> PrimaryExpression()
    */
   public R visit(CompareExpression n, A argu) {
      R _ret=null;
      String a,b;
      a = n.f0.accept(this, argu).toString();
      n.f1.accept(this, argu);
      b = n.f2.accept(this, argu).toString();
      if(!a.equals(b) || !a.equals("int") || !b.equals("int") ){
        System.out.println("Type error");
        System.exit(0);
      }
      _ret = (R)("boolean");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "!="
    * f2 -> PrimaryExpression()
    */
   public R visit(neqExpression n, A argu) {
      R _ret=null;
      String a,b;
      a = n.f0.accept(this, argu).toString();
      n.f1.accept(this, argu);
      b = n.f2.accept(this, argu).toString();
      if(!a.equals(b)){
        System.out.println("Type error");
        System.exit(0);
      }
      _ret = (R)("boolean");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public R visit(PlusExpression n, A argu) {
      R _ret=null;
      String a,b;
      a = n.f0.accept(this, argu).toString();
      n.f1.accept(this, argu);
      b = n.f2.accept(this, argu).toString();
      if(!a.equals(b) || !a.equals("int") || !b.equals("int") ){
        System.out.println("Type error");
        System.exit(0);
      }
      _ret = (R)("int");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public R visit(MinusExpression n, A argu) {
      R _ret=null;
      String a,b;
      a = n.f0.accept(this, argu).toString();
      n.f1.accept(this, argu);
      b = n.f2.accept(this, argu).toString();
      if(!a.equals(b) || !a.equals("int") || !b.equals("int") ){
        System.out.println("Type error");
        System.exit(0);
      }
      _ret = (R)("int");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public R visit(TimesExpression n, A argu) {
      R _ret=null;
      String a,b;
      a = n.f0.accept(this, argu).toString();
      n.f1.accept(this, argu);
      b = n.f2.accept(this, argu).toString();
      if(!a.equals(b) || !a.equals("int") || !b.equals("int") ){
        System.out.println("Type error");
        System.exit(0);
      }
      _ret = (R)("int");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "/"
    * f2 -> PrimaryExpression()
    */
   public R visit(DivExpression n, A argu) {
      R _ret=null;
      String a,b;
      a = n.f0.accept(this, argu).toString();
      n.f1.accept(this, argu);
      b = n.f2.accept(this, argu).toString();
      if(!a.equals(b) || !a.equals("int") || !b.equals("int") ){
        System.out.println("Type error");
        System.exit(0);
      }
      _ret = (R)("int");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   //TODO
   public R visit(ArrayLookup n, A argu) {
      R _ret=null;
      String a,b;
      a = n.f0.accept(this, argu).toString();
      n.f1.accept(this, argu);
      b = n.f2.accept(this, argu).toString();
      n.f3.accept(this, argu);
      if(!a.equals("int[]") || !b.equals("int") ){
        System.out.println("Type error");
        System.exit(0);
      }
      _ret = (R)("int");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public R visit(ArrayLength n, A argu) {
      R _ret=null;
      String a = n.f0.accept(this, argu).toString();
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      if(!a.equals("int[]")){
        System.out.println("Type error");
        System.exit(0);
      }
      _ret = (R)("int");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   //TODO
   public R visit(MessageSend n, A argu) {
      R _ret=null;
      String b = n.f0.accept(this, argu).toString();
      n.f1.accept(this, argu);
      //n.f2.accept(this, argu);
      String a = n.f2.f0.toString();
      n.f3.accept(this, argu);
      ArrayList<String> fp = new ArrayList<String>();
      n.f4.accept(this, (A)fp);
      n.f5.accept(this, argu);


      String ty = getFnKey(b,a);

      if(fnhm.containsKey(ty)){
        _ret = (R)fnhm.get(ty);
        String p = fnparamhm.get(ty);
        String q = fs(fp);
        ArrayList<String> pc = new ArrayList<String>();
        ArrayList<String> qc = new ArrayList<String>();
        for(String temp : p.split(":")){
          if(clsdec.contains(temp)){
            pc.add(temp);
          }
        }
        for(String temp : q.split(":")){
          if(clsdec.contains(temp)){
            qc.add(temp);
          }
        }
        if(!p.equals(q)){

          if(pc.size()==qc.size()){
            for(int i=0;i<pc.size();i++){
              String cs1 = pc.get(i);
              String cs2 = qc.get(i);
              //cs2 can extend cs1
              if(cycExtd.containsKey(cs2)){
                String sss = cycExtd.get(cs2);
                if(!sss.contains(cs1)){
                  //System.out.println("Type error! params dont match for "+ty+" ("+p+","+q+")");
                  System.out.println("Type error");
                  System.exit(0);      
                }
              }
              else{
                System.out.println("Type error");
                System.exit(0);      
              }
            }
          }
          else{
            //System.out.println("Type error! params dont match for "+ty+" ("+p+","+q+")");
            System.out.println("Type error");
            System.exit(0);    
          }
          
        }
        //System.out.println("getting "+ty+" : "+fnhm.get(ty));
      }
      else{
        //System.out.println("Key not found for: "+a);
        System.out.println("Type error");
        System.exit(0);
      }
      //fparams = "";
      return _ret;
   }


   public String fs(ArrayList<String> al){
    String s = "";
    for(String temp: al){
      s = s + temp;
    }
    ////System.out.println("==========="+s);
    return s;
   }
   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public R visit(ExpressionList n, A argu) {
      R _ret=null;
      // String a,b;
      // a = n.f0.accept(this, argu).toString();
      // b = n.f1.accept(this, argu).toString();
      // _ret = (R)(a+b);
      //assign = true;
      ArrayList<String> fp1 = new ArrayList<String>();
      fp1.add(n.f0.accept(this, argu).toString());
      String aa = fs(fp1);
      if(!aa.equals(""))
        ((ArrayList<String>)argu).add(aa);
      n.f1.accept(this, (A)argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public R visit(ExpressionRest n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      //fparams+=":";
      //assign = true;
      //n.f1.accept(this, argu);
      ArrayList<String> fp1 = new ArrayList<String>();
      fp1.add(n.f1.accept(this, argu).toString());
      String aa = fs(fp1);
      if(!aa.equals(""))
        ((ArrayList<String>)argu).add(":"+aa);
      // String a = n.f1.accept(this, argu).toString();
      // _ret = (R)(","+a);
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
   public R visit(PrimaryExpression n, A argu) {
      R _ret=null;
      String a = n.f0.accept(this, argu).toString();
      //if(!a.equals("null"))
      _ret = (R)a;

      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = (R)("int");
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public R visit(TrueLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = (R)("boolean");
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public R visit(FalseLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = (R)("boolean");
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   //DOUBT
   public R visit(Identifier n, A argu) {
      R _ret=null;
      String x = n.f0.toString();
      String var = getVarKey(x);
      if(varhm.containsKey(var)){
      	_ret = (R)varhm.get(var);
      	//deletelater
      	//System.out.println("getting "+var+" : "+varhm.get(var));
      }
      else if(clsdec.contains(x)){
      	_ret = (R)x;
      	//System.out.println("getting "+x+" : class name");
      }
      else{
        //System.out.println("Error! key "+x+" not found!");
        System.out.println("Type error");
        System.exit(0);
      }
      
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "this"
    */
   public R visit(ThisExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      if(!currclass.empty()){
      	String c = currclass.peek();
      	_ret = (R)c;	
      }
      else
      	_ret = (R)("null");
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public R visit(ArrayAllocationExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      _ret = (R)("int[]");
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public R visit(AllocationExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String a = n.f1.accept(this, argu).toString();
      //String a = n.f1.f0.toString();
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      _ret = (R)a;
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public R visit(NotExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String a = n.f1.accept(this, argu).toString();
      if(!a.equals("boolean")){
        System.out.println("Type error");
        System.exit(0);
      }
      _ret = (R)("boolean");
      return _ret;
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public R visit(BracketExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      _ret = (R)n.f1.accept(this, argu).toString();
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> ( IdentifierRest() )*
    */
   public R visit(IdentifierList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Identifier()
    */
   public R visit(IdentifierRest n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

}
