/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guidemo.parser;

import java.io.IOException;
import java.util.LinkedList;
import jcifs.UniAddress;
import jcifs.smb.*;

/**
 *
 * @author Максим
 */
public class SmbFunction {

    private UniAddress domain;
    private NtlmPasswordAuthentication authentication;
    private final String HOSTNAME = "vps-1030232.srv.pa.infobox.ru";
    private final String NETWORK_FOLDER = "smb://vps-1030232.srv.pa.infobox.ru/dekanat/";

    public SmbFunction() {

    }

    public void login(String username, char[] password) throws Exception {

        String spassword = new String(password);
        setDomain(UniAddress.getByName(HOSTNAME));
        setAuthentication(new NtlmPasswordAuthentication(HOSTNAME, username, spassword));
        SmbSession.logon(getDomain(), authentication);

    }

    public LinkedList<String> getList() throws Exception {
        LinkedList<String> fList = new LinkedList<>();
        SmbFile f = new SmbFile(NETWORK_FOLDER, authentication);
        SmbFile[] fArr = f.listFiles();

        for (SmbFile fArr1 : fArr) {
            fList.add(fArr1.getName());
            System.out.println(fArr1.getName());
        }

        return fList;
    }

    public boolean isExist() throws Exception {
        SmbFile sFile = new SmbFile(NETWORK_FOLDER, authentication);

        return sFile.exists();
    }

    public boolean isDir() throws Exception {
        SmbFile sFile = new SmbFile(NETWORK_FOLDER, authentication);

        return sFile.isDirectory();
    }

    public void createDir(String dirName, String path) throws Exception {
        String fullpath = NETWORK_FOLDER + path + dirName;
        SmbFile sFile = new SmbFile(fullpath, authentication);
        while (!sFile.exists()){
            sFile.mkdir();
        }
    }

    public void delete() throws Exception {
        SmbFile sFile = new SmbFile(NETWORK_FOLDER, authentication);
        sFile.delete();
    }

    public long size() throws Exception {
        SmbFile sFile = new SmbFile(NETWORK_FOLDER, authentication);

        return sFile.length();
    }

    public void createFile(String fileName) throws Exception {
        String path = NETWORK_FOLDER + fileName;
        SmbFile sFile = new SmbFile(path, authentication);

        sFile.createNewFile();
    }

    public String getFileName(String path) throws Exception {
        String fullpath = NETWORK_FOLDER + path;
        SmbFile sFile = new SmbFile(fullpath, authentication);

        return sFile.getName();
    }

    public boolean copyFiles(String fileContent, String fileName) {
        boolean successful = false;
        String path = null;
        SmbFile sFile = null;
        SmbFileOutputStream sfos = null;
        try {
            path = NETWORK_FOLDER + fileName;
            sFile = new SmbFile(path, authentication);
            sfos = new SmbFileOutputStream(sFile);
            sfos.write(fileContent.getBytes());
            successful = true;
            System.out.println("File successfully created.");
        } catch (IOException e) {
            successful = false;
            System.err.println("Unable to create file. Cause: "
                    + e.getMessage());
        }
        return successful;
    }

    public UniAddress getDomain() {
        return domain;
    }

    public void setDomain(UniAddress domain) {
        this.domain = domain;
    }

    public NtlmPasswordAuthentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(NtlmPasswordAuthentication authentication) {
        this.authentication = authentication;
    }

}
