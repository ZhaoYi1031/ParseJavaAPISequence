import org.eclipse.jdt.core.dom.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DemoVisitorTest {
    static String jdkPrefix[] = {"java.applet", "java.awt", "java.awt.color", "java.awt.datatransfer", "java.awt.dnd",
            "java.awt.event", "java.awt.font", "java.awt.geom", "java.awt.im", "java.awt.image", "java.awt.image.renderable", "java.awt.im.spi", "java.awt.print", "java.beans", "java.beans.beancontext", "java.io", "java.lang", "java.lang.annotation", "java.lang.instrument", "java.lang.invoke", "java.lang.management", "java.lang.ref", "java.lang.reflect", "java.math", "java.net", "java.nio", "java.nio.channels", "java.nio.channels.spi", "java.nio.charset", "java.nio.charset.spi", "java.nio.file", "java.nio.file.attribute", "java.nio.file.spi", "java.rmi", "java.rmi.activation", "java.rmi.dgc", "java.rmi.registry", "java.rmi.server", "java.security", "java.security.acl", "java.security.cert", "java.security.interfaces", "java.security.spec", "java.sql", "java.text", "java.text.spi", "java.util", "java.util.concurrent", "java.util.concurrent.atomic", "java.util.concurrent.locks", "java.util.jar", "java.util.logging", "java.util.prefs", "java.util.regex", "java.util.spi", "java.util.zip", "javax.accessibility", "javax.activation", "javax.activity", "javax.annotation", "javax.annotation.processing", "javax.crypto", "javax.crypto.interfaces", "javax.crypto.spec", "javax.imageio", "javax.imageio.event", "javax.imageio.metadata", "javax.imageio.plugins.bmp", "javax.imageio.plugins.jpeg", "javax.imageio.spi", "javax.imageio.stream", "javax.jws", "javax.jws.soap", "javax.lang.model", "javax.lang.model.element", "javax.lang.model.type", "javax.lang.model.util", "javax.management", "javax.management.loading", "javax.management.modelmbean", "javax.management.monitor", "javax.management.openmbean", "javax.management.relation", "javax.management.remote", "javax.management.remote.rmi", "javax.management.timer", "javax.naming", "javax.naming.directory", "javax.naming.event", "javax.naming.ldap", "javax.naming.spi", "javax.net", "javax.net.ssl", "javax.print", "javax.print.attribute", "javax.print.attribute.standard", "javax.print.event", "javax.rmi", "javax.rmi.CORBA", "javax.rmi.ssl", "javax.script", "javax.security.auth", "javax.security.auth.callback", "javax.security.auth.kerberos", "javax.security.auth.login", "javax.security.auth.spi", "javax.security.auth.x500", "javax.security.cert", "javax.security.sasl", "javax.sound.midi", "javax.sound.midi.spi", "javax.sound.sampled", "javax.sound.sampled.spi", "javax.sql", "javax.sql.rowset", "javax.sql.rowset.serial", "javax.sql.rowset.spi", "javax.swing", "javax.swing.border", "javax.swing.colorchooser", "javax.swing.event", "javax.swing.filechooser", "javax.swing.plaf", "javax.swing.plaf.basic", "javax.swing.plaf.metal", "javax.swing.plaf.multi", "javax.swing.plaf.nimbus", "javax.swing.plaf.synth", "javax.swing.table", "javax.swing.text", "javax.swing.text.html", "javax.swing.text.html.parser", "javax.swing.text.rtf", "javax.swing.tree", "javax.swing.undo", "javax.tools", "javax.transaction", "javax.transaction.xa", "javax.xml", "javax.xml.bind", "javax.xml.bind.annotation", "javax.xml.bind.annotation.adapters", "javax.xml.bind.attachment", "javax.xml.bind.helpers", "javax.xml.bind.util", "javax.xml.crypto", "javax.xml.crypto.dom", "javax.xml.crypto.dsig", "javax.xml.crypto.dsig.dom", "javax.xml.crypto.dsig.keyinfo", "javax.xml.crypto.dsig.spec", "javax.xml.datatype", "javax.xml.namespace", "javax.xml.parsers", "javax.xml.soap", "javax.xml.stream", "javax.xml.stream.events", "javax.xml.stream.util", "javax.xml.transform", "javax.xml.transform.dom", "javax.xml.transform.sax", "javax.xml.transform.stax", "javax.xml.transform.stream", "javax.xml.validation", "javax.xml.ws", "javax.xml.ws.handler", "javax.xml.ws.handler.soap", "javax.xml.ws.http", "javax.xml.ws.soap", "javax.xml.ws.spi", "javax.xml.ws.spi.http", "javax.xml.ws.wsaddressing", "javax.xml.xpath", "org.ietf.jgss", "org.omg.CORBA", "org.omg.CORBA_2_3", "org.omg.CORBA_2_3.portable", "org.omg.CORBA.DynAnyPackage", "org.omg.CORBA.ORBPackage", "org.omg.CORBA.portable", "org.omg.CORBA.TypeCodePackage", "org.omg.CosNaming", "org.omg.CosNaming.NamingContextExtPackage", "org.omg.CosNaming.NamingContextPackage", "org.omg.Dynamic", "org.omg.DynamicAny", "org.omg.DynamicAny.DynAnyFactoryPackage", "org.omg.DynamicAny.DynAnyPackage", "org.omg.IOP", "org.omg.IOP.CodecFactoryPackage", "org.omg.IOP.CodecPackage", "org.omg.Messaging", "org.omg.PortableInterceptor", "org.omg.PortableInterceptor.ORBInitInfoPackage", "org.omg.PortableServer", "org.omg.PortableServer.CurrentPackage", "org.omg.PortableServer.POAManagerPackage", "org.omg.PortableServer.POAPackage", "org.omg.PortableServer.portable", "org.omg.PortableServer.ServantLocatorPackage", "org.omg.SendingContext", "org.omg.stub.java.rmi", "org.w3c.dom", "org.w3c.dom.bootstrap", "org.w3c.dom.events", "org.w3c.dom.ls", "org.xml.sax", "org.xml.sax.ext", "org.xml.sax.helpers"};

    public static boolean isJdkApi(String s) {
        for(String si:jdkPrefix){
            if(s.startsWith(si)) return true;
        }
        return false;
    }
    public void insertErrorStatus(Con con, String path, int projectId, int disk, int status) throws SQLException {
        con.preSt = con.con.prepareStatement("insert into API_sequence_new(projectId, path, disk, status) values(?,?,?,?)");
        con.preSt.setInt(1, projectId);
        con.preSt.setString(2, path);
        con.preSt.setInt(3,disk);
        con.preSt.setInt(4, status);
        con.executeSql();
    }

    public static String parseMethod(String method) {
        final List<String> ret = new ArrayList();
        int last_id = 0;
        for (int id = 0; id < method.length(); ++id) {
            char c = method.charAt(id);
            if (c >= 'A' && c <= 'Z') {
//                if (_ > 0)
                {
                    ret.add(method.substring(last_id, id).toLowerCase());
                    last_id = id;
                }
            }
        }
        ret.add(method.substring(last_id, method.length()).toLowerCase());
        String ans = "";
        for (String i: ret) {
            if (ans.equals("")) {
                ans = i;
            } else {
                ans = ans + " " + i;
            }
        }
        System.out.println("method ans=" + ans);
        return ans;
    }

    public static String parseComment(String code) {
//        System.out.println(code);
        int pos = -1;
        for (int i = 0; i < code.length() -1; ++i) {
            if (code.charAt(i) == '*' && code.charAt(i+1) == '/') {
                pos = i + 3; // +2 空格 +3: \n
                break;
            }
        }
        if (pos != -1)
            return code.substring(0, pos);
        return code;
    }

    public static String processSentence(String sen) {
//        System.out.println(sen.substring(0,2));
        if (sen.length() >= 2 && sen.substring(0,2).equals("* "))
            return sen.substring(2, sen.length());
        return sen;
    }

    public static String parseCode(String code) {
//        System.out.println(code);
        int pos = -1;
        for (int i = 0; i < code.length() -1; ++i) {
            if (code.charAt(i) == '*' && code.charAt(i+1) == '/') {
                pos = i + 3;
                break;
            }
        }
        if (pos != -1)
            return code.substring(pos, code.length());
        return code;
    }

    public static String getSuffixStringSingle(String api) {
        if (api.replaceAll("\\s+", "").equals(""))
            return "";
        //param:  java.util.Hashtable<java.lang.String,java.lang.Float>
        //return: Map < String , Font > values Font dispose Map < String , Font > clear Map < Font , Font > values Font dispose Map < Font , Font > clear
        int pos = api.lastIndexOf(".");
        if (pos != -1)
            return api.substring(pos + 1, api.length());
        return api;
    }

    public static String getSuffixString(String api) {

//        final List<String> ret = new ArrayList();
//
//        //param:  java.util.Hashtable<java.lang.String,java.lang.Float>
//        //return: Map < String , Font > values Font dispose Map < String , Font > clear Map < Font , Font > values Font dispose Map < Font , Font > clear
//        String[] apis = api.split("(<|>|\\s+)");
//        for (String i : apis){
////        for (int i = 0; i < apis.length(); ++i){
//            ret.add(getSuffixStringSingle(i));
//        }
//        String ans = "";
//        for (String i : ret){
//            ans = ans + " " + i;
//        }
//        System.out.println("!!!api="+api);
//        System.out.println("ans ="+ans);
//        return api;
        final List<String> ret = new ArrayList();
        api = api.replaceAll("\\s+", " ");
        int last_id = 0;
        api = api + " ";
        for (int _ = 0; _ < api.length(); ++_){
            boolean flag = false;
            char c = api.charAt(_);
            if (c == ',') {
                String tmp = api.substring(last_id, _);
                ret.add(getSuffixStringSingle(tmp));
                ret.add(",");
                flag = true;
            } else if (c == '<') {
                String tmp = api.substring(last_id, _);
                ret.add(getSuffixStringSingle(tmp));
                ret.add("<");
                flag = true;
            } else if (c == '>') {
                String tmp = api.substring(last_id, _);
                ret.add(getSuffixStringSingle(tmp));
                ret.add(">");
                flag = true;
            } else if (c == ' ') {
                String tmp = api.substring(last_id, _);
                ret.add(getSuffixStringSingle(tmp));
                flag = true;
            }
            if (flag) {
                last_id = _ + 1;
            }
        }
        String ans = "";
        for (String i : ret){
            if (!i.equals("")) {
                if (!ans.equals(""))
                    ans = ans + " " + i;
                else
                    ans = i;
            }
        }
//        System.out.println("!!!api="+api);
//        System.out.println("ans ="+ans);
//        ans = api;
        return ans;
    }

    public static String getUrl(String path, CompilationUnit comp, int position) {
        String urlPath = "";

//                        System.out.println(path);
//                        path = "/sdpdata2/zhaoyi/downloads/150/gglinux___xingwen___34883091/src/StaffIndex.java";

        String[] pathStrs = path.split("/");
        if (pathStrs.length > 6) {
//                            System.out.println("-"+pathStrs[1]); //sdpdata1
            StringBuilder url = new StringBuilder("https://github.com/");
            String strUserProject = pathStrs[5];
            String[] strUserProjectStrs = strUserProject.split("___");
            if (strUserProjectStrs.length == 3) {
//                                System.out.println("length" + strUserProjectStrs.length);
                String userName = strUserProjectStrs[0];
                String projectName = strUserProjectStrs[1];
                // https://github.com/cbg-ethz/rnaiutilities/blob/master/rnaiutilities/utility/files.py#L108
                url.append(userName+"/");
                url.append(projectName+"/");
                url.append("blob/master");
                for (int i = 6; i < pathStrs.length; ++i) {
                    url.append("/" + pathStrs[i]);
                }

                int lineNumber = comp.getLineNumber(position);
//                                System.out.println("lineNumer = " + lineNumber);

                url.append("#L"+lineNumber);

                urlPath = url.toString();
                return urlPath;
//                                System.out.println(urlPath);
            }

        }
        return "";
    }

    public DemoVisitorTest(final String path, final int projectId, final int disk, final Con con) throws SQLException {
        CompilationUnit comp = null;
        try {
            comp = JdtAstUtil.getCompilationUnit(path);
        }
        catch (Exception e) {
            System.out.println("ERROR : Parse file >> " + path);
            insertErrorStatus(con, path, projectId, disk, 4);
            e.printStackTrace();
            return;
        }
//        DemoVisitor visitor = new DemoVisitor();
//        MyVisitor visitor = new MyVisitor();
        final CompilationUnit finalComp = comp;
        comp.accept(new ASTVisitor() {
            @Override
            public boolean visit(MethodDeclaration node) {

                Block block = node.getBody();
                final List ret = new ArrayList();
                final List retFull = new ArrayList();
                ret.add(node.getName());
                Javadoc docs = node.getJavadoc();
                if(docs == null) {
                    return true;
                }
                List tags = docs.tags();
                boolean haveJavadoc = false;
                if(tags.size() > 0) {
                    String fullDocs = tags.get(0).toString().trim();
                    boolean isDocs = true;
                    for(int i = 0; i < fullDocs.length(); i++) {
                        char ch = fullDocs.charAt(i);
                        if(ch == '*' || ch == ' ') continue;
                        if(ch == '@') {
                            isDocs = false;
                        }
                        break;
                    }
                    if(isDocs) {
                        retFull.add(fullDocs);
                        String[] sentences = fullDocs.split("\\. ");
                        for(String sentence: sentences) {
                            if(sentence.trim().length() > 0) {
                                ret.add(sentence.trim());
                                haveJavadoc = true;
                                break;
                            }
                        }
                    }

                }
                if(!haveJavadoc) return true;
                if(block != null){
                    block.accept(new ASTVisitor() {

                        @Override
                        public void endVisit(MethodInvocation node) {
                            Expression expression = node.getExpression();
                            if(expression != null) {
                                ITypeBinding  typeBinding = expression.resolveTypeBinding();
                                if (typeBinding != null) {
                                    String qualifiedName = typeBinding.getQualifiedName();
//                                    retFull.add(qualifiedName + " " + node.getName());
                                    String qualifiedFullName = qualifiedName + " " + node.getName();


//                                    System.out.println("qualifiedFullName=" + qualifiedFullName);
                                    getSuffixString(qualifiedFullName);
                                    if(retFull.size() <= 1) retFull.add(getSuffixString(qualifiedFullName));
                                    else retFull.set(1, retFull.get(1) + " " + getSuffixString(qualifiedFullName));
                                    if(isJdkApi(qualifiedName)) {
//                                        ret.add(qualifiedName + " " + node.getName());
                                        if(ret.size() <= 2)ret.add(getSuffixString(qualifiedFullName));
                                        else ret.set(2, ret.get(2) + " " + getSuffixString(qualifiedFullName));
                                    }
                                }
                            }

                        }

                        public void endVisit(ClassInstanceCreation node) {
                            ITypeBinding binding = node.getType().resolveBinding();
                            if(binding == null) return ;
                            String qualifiedName = binding.getQualifiedName();

//                            System.out.println("New qualifiedFullName=" + qualifiedName);
                            getSuffixString(qualifiedName);
                            String qualifiedFullName = qualifiedName + " new";
//                            retFull.add(qualifiedName + " " + "new");
                            if(retFull.size() <= 1) retFull.add(getSuffixString(qualifiedFullName));
                            else retFull.set(1, retFull.get(1) + " " + getSuffixString(qualifiedFullName));
                            if(isJdkApi(qualifiedName)) {
//                                ret.add(qualifiedName + " " + "new");
                                if(ret.size() <= 2) ret.add(getSuffixString(qualifiedFullName));
                                else ret.set(2, ret.get(2) + " " + getSuffixString(qualifiedFullName));
                            }

                        }
                    });
                }

                if(ret.size() > 2) {
//                    System.out.println(path);
//                    System.out.println(projectId);
//                    System.out.println(ret);
//                    System.out.println(retFull);
//                    System.out.println(disk);
//                    String sqlOrder = "insert into API_sequence(projectId, method, path, disk, doc, sentence, apis, jdkapis, status) values("
//                            + Integer.toString(projectId) + ", '" + ret.get(0) + "', '" + path + "', " + Integer.toString(disk) + ", '"
//                            + retFull.get(0).toString().replaceAll("\\\\", "\\\\").replaceAll("\'", "\\'")
//                            + "', '" +
//                            ret.get(1).toString().replaceAll("\\\\", "\\\\").replaceAll("\'", "\\'")
//                            + "', '" + retFull.get(1) + "', '" + ret.get(2) + "', 1);";

//                    System.out.println(sqlOrder);

                    if (ret.get(1).toString().equals("") || retFull.get(0).toString().equals("") ||
                            node.toString().equals("")) {
                        System.out.println("get Null "+ projectId);
                    }else {


                    try {

                        con.preSt = con.con.prepareStatement("insert into Code_Element(projectId, method, path, disk, " +
                                "doc, sentence, apis, jdkapis, status, code, url," +
                                "comment, apisSeq, jdkapisSeq, methodSeq) "
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

//                        1:        projectId
//                        2:        method
//                        3:        path
//                        4:        disk
//                        5:        doc
//                        6:        sentence
//                        7:        apis
//                        8:        jdkapis
//                        9:        status
//                       10:        code_new
//                       11:        url_new

//                       12:        comment
//                       13:        apisSeq
//                       14:        jdksapiSeq
//                       15:        methodSeq



                        con.preSt.setInt(1, projectId);
                        String method = ret.get(0).toString();
                        con.preSt.setString(2, method);
                        con.preSt.setString(3,path);
                        con.preSt.setInt(4, disk);
                        con.preSt.setString(5, processSentence(retFull.get(0).toString()));
                        con.preSt.setString(6,  processSentence(ret.get(1).toString()));
                        String apis = retFull.get(1).toString();
                        con.preSt.setString(7, apis);
                        String jdkapis = (String) ret.get(2).toString();
                        con.preSt.setString(8, jdkapis);
                        con.preSt.setInt(9,1);
                        String code = node.toString();
                        con.preSt.setString(10, parseCode(code));

                        String url = getUrl(path, finalComp, node.getStartPosition());
                        System.out.println(url);
                        con.preSt.setString(11, url);
//                        System.out.println("hashcode: " + Integer.toString(con.hashCode()));

                        con.preSt.setString(12, parseComment(code));
                        con.preSt.setString(13, getSuffixString(apis));
                        con.preSt.setString(14, getSuffixString(jdkapis));
                        con.preSt.setString(15, parseMethod(method));
                        con.executeSql();



//                        method = main
//                        apis = java.util.Hashtable<java.lang.String,java.lang.Float> new
//                                method = make
//                        apis = java.lang.Long new  java.util.Hashtable get  st.ata.util.FPGenerator new  java.util.Hashtable put
//                                method = extend
//                        apis = java.lang.CharSequence length  java.lang.CharSequence charAt
//                        method = extend8
//                        apis = java.lang.String length  java.lang.String charAt
//                        *************************************************************************
//                        method = main
//                        apis = Hashtable < String , Float > new
//                                method = make
//                        apis = Long new Hashtable get FPGenerator new Hashtable put
//                                method = extend
//                        apis = CharSequence length CharSequence charAt
//                                method = extend8
//                        apis = String length String charAt


//                        System.out.println("method = " + ret.get(0).toString());
//                        System.out.println(parseMethod(ret.get(0).toString()));
//                        System.out.println("apis = " + retFull.get(1).toString());
//                        apis = java.lang.Long new|java.util.Hashtable get|st.ata.util.FPGenerator new|java.util.Hashtable put
//                                apis = java.lang.CharSequence length|java.lang.CharSequence charAt
//                                apis = java.lang.String length|java.lang.String charAt
//                        System.out.println("code_new = " + parseCode(node.toString()));
                    } catch (SQLException e) {
                        e.printStackTrace();
//                        System.out.println("ERROR INSERT : " + sqlOrder);
                        System.out.println("ERROR INSERT : " + con.preSt.toString());
//                        String errorSqlError = "insert into API_sequence(projectId, path, disk, status) values(" +
//                                Integer.toString(projectId) + ", '" + path + "', " + Integer.toString(disk) + ", 0);";
                        try {
                            con.preSt = con.con.prepareStatement("insert into Code_Element(projectId, method, path, disk, status) "
                            + "values(?,?,?,?,?)");
                            con.preSt.setInt(1,projectId);
                            con.preSt.setString(2,ret.get(0).toString());
                            con.preSt.setString(3,path);
                            con.preSt.setInt(4,disk);
                            con.preSt.setInt(5,0);
                            con.executeSql();
                        } catch (SQLException e1) {
                            System.out.println("ERROR INSERT errorinfo : " + con.preSt.toString());
                            e1.printStackTrace();
                        }
                    }
                }}
                return true;
            }
        }
        );
    }

    public static void main(String args[]) throws SQLException, ClassNotFoundException {
        DemoVisitorTest test = new DemoVisitorTest
                ("/home/zy/zy/150/gglinux___xingwen___34883091/src/StaffIndex.java", 10001, 1, new Con());
//        SubRegistry x = new SubRegistry();
//        System.out.println(getSuffixString("able"));
//        System.out.println("sub:"+("abc".substring(0,0)));
//        System.out.println(processSentence("* Input: An attack graph G with individual sc"));
    }
}  