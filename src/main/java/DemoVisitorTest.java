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
        con.preSt = con.con.prepareStatement("insert into API_sequence(projectId, path, disk, status) values(?,?,?,?)");
        con.preSt.setInt(1, projectId);
        con.preSt.setString(2, path);
        con.preSt.setInt(3,disk);
        con.preSt.setInt(4, status);
        con.executeSql();
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
                                    if(retFull.size() <= 1) retFull.add(qualifiedFullName);
                                    else retFull.set(1, retFull.get(1) + "|" + qualifiedFullName);
                                    if(isJdkApi(qualifiedName)) {
//                                        ret.add(qualifiedName + " " + node.getName());
                                        if(ret.size() <= 2)ret.add(qualifiedFullName);
                                        else ret.set(2, ret.get(2) + "|" + qualifiedFullName);
                                    }
                                }
                            }

                        }

                        public void endVisit(ClassInstanceCreation node) {
                            ITypeBinding binding = node.getType().resolveBinding();
                            if(binding == null) return ;
                            String qualifiedName = binding.getQualifiedName();
                            String qualifiedFullName = qualifiedName + " new";
//                            retFull.add(qualifiedName + " " + "new");
                            if(retFull.size() <= 1) retFull.add(qualifiedFullName);
                            else retFull.set(1, retFull.get(1) + "|" + qualifiedFullName);
                            if(isJdkApi(qualifiedName)) {
//                                ret.add(qualifiedName + " " + "new");
                                if(ret.size() <= 2) ret.add(qualifiedFullName);
                                else ret.set(2, ret.get(2) + "|" + qualifiedFullName);
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

                    try {
                        con.preSt = con.con.prepareStatement("insert into API_sequence(projectId, method, path, disk, doc, sentence, apis, jdkapis, status) "
                            + "values(?,?,?,?,?,?,?,?,?)");
                        con.preSt.setInt(1, projectId);
                        con.preSt.setString(2, ret.get(0).toString());
                        con.preSt.setString(3,path);
                        con.preSt.setInt(4, disk);
                        con.preSt.setString(5, retFull.get(0).toString());
                        con.preSt.setString(6,  ret.get(1).toString());
                        con.preSt.setString(7,  retFull.get(1).toString());
                        con.preSt.setString(8, (String) ret.get(2).toString());
                        con.preSt.setInt(9,1);


//                        System.out.println("hashcode: " + Integer.toString(con.hashCode()));
                        con.executeSql();
                    } catch (SQLException e) {
                        e.printStackTrace();
//                        System.out.println("ERROR INSERT : " + sqlOrder);
                        System.out.println("ERROR INSERT : " + con.preSt.toString());
//                        String errorSqlError = "insert into API_sequence(projectId, path, disk, status) values(" +
//                                Integer.toString(projectId) + ", '" + path + "', " + Integer.toString(disk) + ", 0);";
                        try {
                            con.preSt = con.con.prepareStatement("insert into API_sequence(projectId, method, path, disk, status) "
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
                }
                return true;
            }
        }
        );
    }

    public static void main(String args[]) {

//        DemoVisitorTest test = new DemoVisitorTest
//                ("/home/luckcul/developSpace/test/CharacterDecoder.java", 10001, 1);
//        SubRegistry x = new SubRegistry();
    }
}  