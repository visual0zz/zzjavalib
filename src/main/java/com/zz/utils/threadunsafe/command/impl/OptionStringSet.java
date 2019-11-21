package com.zz.utils.threadunsafe.command.impl;

import java.util.HashSet;

public class OptionStringSet {
    HashSet<String> shortNames=new HashSet<>();
    HashSet<String> fullNames=new HashSet<>();
    public  OptionStringSet(){ }
    public OptionStringSet(OptionStringSet optionStringSet) {
        shortNames.addAll(optionStringSet.shortNames);
        fullNames.addAll(optionStringSet.fullNames);
    }

    void addOption(Option option){
        if(option.shortName!=null)
            shortNames.add(option.shortName);
        if(option.fullName!=null)
            fullNames.add(option.fullName);
    }
    void addShorts(String shorts){
        for(char c:shorts.toCharArray()){
            shortNames.add(String.valueOf(c));
        }
    }
    void addLong(String Long){
        fullNames.add(Long);
    }
    OptionStringSet sub(OptionStringSet set2){
        OptionStringSet result=new OptionStringSet(this);
        for(String s:this.shortNames){
            if(set2.shortNames.contains(s))
                result.shortNames.remove(s);
        }
        for(String s:this.fullNames){
            if(set2.fullNames.contains(s))
                result.fullNames.remove(s);
        }
        return result;
    }
    boolean isEmpty(){
        return shortNames.isEmpty()&&fullNames.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder res=new StringBuilder();
        res.append("{");
        for(String s:shortNames){
            res.append(" -");
            res.append(s);
        }
        for(String f:fullNames){
            res.append(" --");
            res.append(f);
        }
        res.append(" }");
        return res.toString();
    }
}
