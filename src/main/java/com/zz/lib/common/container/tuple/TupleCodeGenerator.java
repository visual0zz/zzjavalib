package com.zz.lib.common.container.tuple;

import com.zz.lib.common.CheckUtil;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;

final class TupleCodeGenerator {
    public static void main(String[] args) throws Exception {
        String folder="src/main/java/com/zz/lib/common/container/tuple";
        int tuples=30;
        for(int n=2;n<=tuples;n++){
            File f=new File(folder,"Tuple"+n+".java");
            if(!f.exists()){
                f.createNewFile();
            }
            PrintStream p=new PrintStream(Files.newOutputStream(f.toPath()));
            p.print(tupleNCode(n));
            p.flush();
            p.close();
        }
        PrintStream p=new PrintStream(Files.newOutputStream(new File(folder, "/Tuple.java").toPath()));
        p.print(tupleRootCode(tuples));
    }
    static String tupleRootCode(int n){
        CheckUtil.assertTrue(n>0);
        StringBuilder code=new StringBuilder();
        code.append("package com.zz.lib.common.container.tuple;\n" +
                "import com.zz.lib.common.exception.InvalidOperationException;\n" +
                "import java.io.Serializable;\n" +
                "\n\n" +
                "public class Tuple implements Cloneable, Comparable<Tuple>, Serializable {\n" +
                "    private final static long serialVersionUID=1L;\n" +
                "    private final Object[]contents;\n" +
                "    public Tuple( Object...vs) {\n" +
                "        contents=vs;\n" +
                "    }\n" +
                "    public int size(){\n" +
                "        return contents.length;\n" +
                "    }\n" +
                "    public Object getVn(int i){\n" +
                "        return contents[i-1];\n" +
                "    }\n" +
                "\n" +
                "    private static <T> int compare(T o1, T o2) {\n" +
                "        if(o1==null && o2==null){\n" +
                "            return 0;\n" +
                "        }\n" +
                "        if(o1==null){\n" +
                "            return -1;\n" +
                "        }\n" +
                "        if(o2==null){\n" +
                "            return 1;\n" +
                "        }\n" +
                "        if(o1 instanceof Comparable){\n" +
                "            return  ((Comparable) o1).compareTo(o2);\n" +
                "        }\n" +
                "        throw new InvalidOperationException(\"not comparable.\");\n" +
                "    }\n" +
                "    @Override\n" +
                "    public int compareTo(Tuple v) {\n" +
                "        for(int i=1,n = Math.min(this.size(), v.size()); i <= n; ++i) {\n" +
                "            int result = compare(this.getVn(i), v.getVn(i));\n" +
                "            if (result != 0) {\n" +
                "                return result;\n" +
                "            }\n" +
                "        }\n" +
                "        return Integer.compare(this.size(), v.size());\n" +
                "    }\n" +
                "    @Override\n" +
                "    public Tuple clone(){\n" +
                "        return new Tuple(this.contents);\n" +
                "    }\n");
        for(int i=2;i<=n;i++){
            code.append("\n    public static <");
            for(int j=1;j<=i;j++){
                code.append("T").append(j);
                if(j!=i)code.append(",");
            }
            code.append("> Tuple").append(i).append("<");
            for(int j=1;j<=i;j++){
                code.append("T").append(j);
                if(j!=i)code.append(",");
            }
            code.append("> of").append("(");
            for(int j=1;j<=i;j++){
                code.append("T").append(j).append(" v").append(j);
                if(j!=i)code.append(",");
            }
            code.append("){").append("\n        return new Tuple").append(i).append("(");
            for(int j=1;j<=i;j++){
                code.append("v").append(j);
                if(j!=i)code.append(",");
            }
            code.append(");\n    }");
        }
        code.append("\n}");
        return code.toString();
    }
    static String tupleNCode(int n){
        CheckUtil.assertTrue(n>0);
        StringBuilder code=new StringBuilder();
        code.append("package com.zz.lib.common.container.tuple;\n\n" +
                "\n");
        code.append("public final class Tuple").append(n).append(" <");
        for(int i=1;i<=n;i++){
            code.append("T").append(i);
            if(i!=n)code.append(",");
        }
        code.append("> extends Tuple{");
        code.append("\n    public Tuple").append(n).append("(");
        for(int i=1;i<=n;i++){
            code.append("T").append(i).append(" v").append(i);
            if(i!=n)code.append(",");
        }
        code.append(") {\n        super(");
        for(int i=1;i<=n;i++){
            code.append("v").append(i);
            if(i!=n)code.append(",");
        }
        code.append(");\n    }");
        for(int i=1;i<=n;i++){
            code.append("\n    public T").append(i).append(" getV").append(i).append("(){")
                    .append("\n        return (T").append(i).append(")getVn(").append(i).append(");\n    }");
        }
        code.append("\n}");
        return code.toString();
    }
}
