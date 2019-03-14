import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;

class FindFileVisitor extends SimpleFileVisitor<Path> {
    private String fileSuffix = ".java";
    private int cnt = 0;
    private Con con;
    private static int disk;
    int lastProjectId;
    boolean lastDiskRight;
    public FindFileVisitor(Con con, int disk) {
        this.con = con;
        this.disk = disk;
        this.lastProjectId = -111;
        this.lastDiskRight = true;
    }

    public FindFileVisitor(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }
    public void insertErrorStatus(String path, int projectId, int status) throws SQLException {
        this.con.preSt = this.con.con.prepareStatement("insert into Code_Element(projectId, path, disk, status) values(?,?,?,?)");
        this.con.preSt.setInt(1, projectId);
        this.con.preSt.setString(2, path);
        this.con.preSt.setInt(3,disk);
        this.con.preSt.setInt(4, status);
        this.con.executeSql();
    }
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if(file.toString().endsWith(fileSuffix)) {
//            System.out.println(file.toString());
            cnt ++;
            if(cnt % 100 == 0) {
                System.out.println(file.toString() + "\n\tcount : " + cnt);
            }

            String filePath = file.toString();
            String[] segment = filePath.split("/");
            if(segment.length > 5) {
                String projectInfo = segment[5];
                String[] infos = projectInfo.split("___");
                if(infos.length >= 3) {
                    // try to parse the project id
                    int projectId =lastProjectId;
                    try {
                        projectId = Integer.parseInt(infos[infos.length-1].replaceAll("[^0-9]", ""));
                    }
                    catch (Exception e) {
                        System.out.println("ERROR : parse projectId error");
                        try {
                            insertErrorStatus(file.toString(), -111, 2);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                        return FileVisitResult.CONTINUE;
                    }

                    // if find new project(id)
                    if(projectId != this.lastProjectId) {
                        // skip the wrong disk number project.
                        int diskInDB = -123;
                        try {
                            diskInDB = this.con.executeSqlRetInt(projectId);
//                            System.out.println(diskInDB);
                        } catch (SQLException e) {
                            System.out.println("ERROR: find origin disk of projectId boom");
                            e.printStackTrace();
                        }
                        if(diskInDB != this.disk && diskInDB != -1) this.lastDiskRight = false;
                        else this.lastDiskRight = true;
                        if(!this.lastDiskRight) {
                            this.lastProjectId = projectId;
                            return FileVisitResult.SKIP_SIBLINGS;
                        }
                        if(this.lastProjectId != -111) {
                            try {
                                String updateSql = "update java_repositories set isParsed = 1 where projectId = " + Integer.toString(this.lastProjectId);
                                con.executeSql(updateSql);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                System.out.println("ERROR UPDATE: " + projectId);
                            }
                        }
                        // delete the parsed records with projectId == new project id
                        String deleteSql = "delete from Code_Element where projectId = " + Integer.toString(projectId);
                        try {
                            con.executeSql(deleteSql);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        this.lastProjectId = projectId;
                    }
                    else {
                        // thisProjectId == lastProjectId
                        if(!this.lastDiskRight) return FileVisitResult.SKIP_SIBLINGS;
                    }
                    try{
                        new DemoVisitorTest(file.toString(), projectId, this.disk, con);
                    }
                    catch (Exception e) {
                        System.out.println("ERROR : TOP  DemoVisitorTest exception");
                        System.out.println(file.toString());
                        try {
                            insertErrorStatus(file.toString(), projectId, 3);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    catch (Error e) {
                        System.out.println("ERROR : TOP DemoVisitorTest error");
                        System.out.println(file.toString());
                        try {
                            insertErrorStatus(file.toString(), projectId, 3);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
                else {
//                    System.out.println(segment[0] + "\t" + segment[1] + "\t" + segment[2] + "\t" + segment[3] + "\t" + segment[4]);
//                    System.out.println(infos.length);
                    System.out.println("ERROR: NOT FIND projectId in " + filePath);
                    try {
                        insertErrorStatus(file.toString(), -222, 5);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                System.out.println("ERROR: TOO SHORT path -- " + filePath);
                try {
                    insertErrorStatus(file.toString(), -222, 6);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }
        return FileVisitResult.CONTINUE;
    }
}


public class SubThread extends Thread {
    private String path;
    private int disk;
    Con connect;
    public SubThread(String path, int disk) throws SQLException, ClassNotFoundException {
        this.path = path;
        this.disk = disk;
        this.connect = new Con();
//        System.out.println("hellow " + Integer.toString(this.connect.con.hashCode()));
    }

    @Override
    public void run() {
        Path dir = Paths.get(path);
        FindFileVisitor finder = new FindFileVisitor(this.connect, this.disk);
        try {
            Files.walkFileTree(dir, finder);
        } catch (IOException e) {
            System.out.println("ERROR walkFileTree");
            e.printStackTrace();
        }

        // close mysql connection
        try {
            this.connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Finish: Thread in path" + path);
    }
}
