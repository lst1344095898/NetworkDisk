package com.lst.service.serviceImpl;

import com.lst.dao.AdminDao;
import com.lst.entity.FileEntity;
import com.lst.entity.State;
import com.lst.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public boolean saveFile(String fileName, String filePath, String type, long fileSize, String username) {
        // TODO Auto-generated method stub
        if (adminDao.saveFile(fileName, filePath, type, fileSize,username,getDate())!=0) {
            return true;
        }
        return false;
    }

    @Override
    public State<ArrayList<FileEntity>> getAllFile() {
        State<ArrayList<FileEntity>> state=new State<ArrayList<FileEntity>>();
        ArrayList<FileEntity> statEntities=adminDao.getAllFile();
        if (statEntities!=null) {
            state.setState_id(true);
            state.setData(statEntities);
            state.setError(null);
            return state;
        }
        state.setState_id(false);
        state.setData(null);
        state.setError(null);
        return state;
    }

    @Override
    public State<ArrayList<FileEntity>> getAllImages() {
        State<ArrayList<FileEntity>> state=new State<ArrayList<FileEntity>>();
        ArrayList<FileEntity> statEntities= new ArrayList<FileEntity>();
        statEntities=adminDao.getAllImages();
        if (statEntities!=null) {
            state.setState_id(true);
            state.setData(statEntities);
            state.setError(null);
            return state;
        }
        state.setState_id(false);
        state.setData(null);
        state.setError(null);
        return state;
    }
    @Override
    public FileEntity getFilePathByFileId(String fileid) {
        int id= Integer.parseInt(fileid);
        FileEntity filePath=adminDao.getFilePathByFileId(id);
        return filePath;
    }
    public String getDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String data=(String) formatter.format(date);
        return data;
    }
}
