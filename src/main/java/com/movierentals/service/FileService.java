package com.movierentals.service;

import com.movierentals.model.Client;
import com.movierentals.model.Movie;
import com.movierentals.model.RentalInfo;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FileService {

    public boolean create(String fileName, String name) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName,true));
            writer.append(name.toLowerCase()+"; ");
            if(getlineCount(fileName)>0){
                writer.append(getlineCount(fileName)+1+"; ");
            }else{
                writer.append("1;");
            }
            writer.append("false\n");
            return true;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean update(String fileName, String name, boolean flag) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line, linetoDelete = getline(fileName,name,false).get(0);
            StringBuffer inputBuffer = new StringBuffer();
            while (((line = reader.readLine()) != null)) {
                inputBuffer.append(line);
                inputBuffer.append("\n");
            }
            String inputStr = inputBuffer.toString();
            reader.close();
            if(flag){
                inputStr = inputStr.replace(linetoDelete, linetoDelete.replace(parseLine(linetoDelete)[2],"true"));
            }else{
                inputStr = inputStr.replace(linetoDelete, linetoDelete.replace(parseLine(linetoDelete)[2],"false"));
            }
            FileOutputStream fileOut = new FileOutputStream(fileName);
            fileOut.write(inputStr.getBytes());
            fileOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List get(String fileName, String name) {
        List<String> lines = null;
        try{
            lines = getClientInfo(fileName, name,true);
            return lines;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private List<String> getClientInfo(String fileName, String name, boolean multiple) {
        BufferedReader reader = null;
        List lines = new ArrayList();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while(line != null){
                if(parseLine(line)[2].equals(name) && parseLine(line)[1].isEmpty()){
                    if(multiple){
                        lines.add(line);
                    }else{
                        lines.add(line);
                        return lines;
                    }
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }


    public List getAll(String fileName, boolean flag) {
        List firstList = null,lastList = null;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            line = reader.readLine();
            firstList = new ArrayList();
            lastList = new ArrayList();
            while(line != null){
                if(line.endsWith("true")){
                    firstList.add(line);
                }
                if(line.endsWith("false")){
                    lastList.add(line);
                }
                line = reader.readLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(flag){
                return firstList;
            }else {
                firstList.addAll(lastList);
                return firstList;
            }
        }
    }

    private List<String> getline(String fileName, String name, boolean multiple) {
        BufferedReader reader = null;
        List lines = new ArrayList();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while(line != null){
                if(line.contains(name)){
                    if(multiple){
                        lines.add(line);
                    }else{
                        lines.add(line);
                        return lines;
                    }
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    private String[] parseLine(String line) {
        return line.split("; ");
    }

    private long getlineCount(String fileName) {
        Stream<String> lines = null;
        BufferedReader reader = null;
        long lineCount = 1;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            lines = reader.lines();
            lineCount = lines.count();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lineCount;
        }
    }

    public boolean updateRentalInfo(RentalInfo info){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(info.getFile()));
            String line, linetoDelete = getRentalInfoLine(info.getFile(),info.getId());
            String[] linetoDeleteArray = parseLine(linetoDelete);
            StringBuffer inputBuffer = new StringBuffer();
            while (((line = reader.readLine()) != null)) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            String inputStr = inputBuffer.toString();
            reader.close();
            if(!linetoDeleteArray[1].trim().isEmpty()){
                throw new Exception("Already returned");
            }
            linetoDeleteArray[1]=info.getInDate();
            linetoDeleteArray[5]=getAmount(linetoDeleteArray[0],linetoDeleteArray[1]);
            String newLinetoDelete = "";
            for (String word:linetoDeleteArray) {
                newLinetoDelete = newLinetoDelete + word.trim() + "; ";
            }
            inputStr = inputStr.replace(linetoDelete, newLinetoDelete);
            FileOutputStream fileOut = new FileOutputStream(info.getFile());
            fileOut.write(inputStr.getBytes());
            fileOut.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getRentalInfoLine(String fileName, int rentId) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while(line != null){
                if(parseLine(line)[4].trim().replace(";","").equalsIgnoreCase(""+rentId)){
                    return line;
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
    public boolean createRentalInfo(RentalInfo rentalInfo) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(rentalInfo.getFile(),true));
            writer.append(rentalInfo.getOutDate()+"; ");
            writer.append("; ");
            writer.append(getId(new Client().getFile(),rentalInfo.getClient()).trim()+"; ");
            writer.append(getId(new Movie().getFile(),rentalInfo.getMovie()).trim()+"; ");
            if(getlineCount(rentalInfo.getFile())>0){
                rentalInfo.setId((int) (getlineCount(rentalInfo.getFile())+1));
                writer.append(getlineCount(rentalInfo.getFile())+1+"; ");
            }else{
                writer.append("1; ");
            }
            writer.append("$0");
            return true;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getAmount(String outDate, String inDate) {
        int fine = 0, noofDays = Integer.parseInt(inDate.split("/")[0])-Integer.parseInt(outDate.split("/")[0]);

        while( noofDays > 8){
            fine = fine + 5;
            noofDays--;
        }
        return " $"+fine;
    }

    public String getId(String fileName, String name) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while(line != null){
                if(line.contains(name.toLowerCase())){
                    return parseLine(line)[1];
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List getMovieHistory(String fileName, String movie) {
        BufferedReader reader = null;
        List lines = new ArrayList();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            String[] lineArray;
            String movieId = getId(new Movie().getFile(),movie.toLowerCase());
            while(line != null){
                lineArray = (line.split("; "));
                if(lineArray[3].trim().equals(movieId.trim())){
                    lines.add(line);
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(lines);
        return lines;
    }

    public void checkClientHistroy(RentalInfo rentalInfo) throws Exception {
        BufferedReader reader = null;
        List lines = new ArrayList();
        try {
            reader = new BufferedReader(new FileReader(rentalInfo.getFile()));
            String line = reader.readLine();
            String[] lineArray;
            String clientId = getId(new Client().getFile(),rentalInfo.getClient());
            while(line != null){
                lineArray = (line.split("; "));
                if (parseLine(getline(new Client().getFile(),rentalInfo.getClient(),false).get(0))[2].trim().equalsIgnoreCase("true")){
                    throw new Exception("client is deleted");
                }
                if(lines.size()>3){
                    throw new Exception("Client is unable to rent a movie");
                }
                if(lineArray[2].trim().equals(clientId.trim())&&lineArray[1].trim().isEmpty()){
                    lines.add(line);
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List getAll(String fileName) {
        List list = null;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            line = reader.readLine();
            list = new ArrayList();
            while(line != null){
                list.add(line);
                line = reader.readLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    public void checkMovieHistroy(String fileName, String movie) throws Exception{
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        while(line != null){
            if(parseLine(line)[2].equalsIgnoreCase("true") && parseLine(line)[0].equalsIgnoreCase(movie)){
                    throw new Exception("Movie is not available");
            }
            line = reader.readLine();
        }
        reader.close();
    }

    public String getMovieName(int id) {
        String name = null;
        String movieId = parseLine(getRentalInfoLine(new RentalInfo().getFile(),id))[3];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new Movie().getFile()));
            String line = reader.readLine();
            while(line != null){
                if(parseLine(line)[1].contains(""+movieId)){
                    return parseLine(line)[0];
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    public List getClient(String fileName, String name) {
        BufferedReader reader = null;
        List lines = new ArrayList();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while(line != null){
                if(parseLine(line)[0].equals(name)){
                        lines.add(line);
                        return lines;
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;

    }
}
