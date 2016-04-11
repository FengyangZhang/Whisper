package com.bupt.johnfrey.whisper.NaiveBayesian;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class TrainData {
	private List<List<String>> trainData;
	private List<Integer> trainDataClass;

	public void init(){
		trainData = new ArrayList<List<String>>();
		trainDataClass = new ArrayList<Integer>();
		trainData.add(Arrays.asList(new String[]{"my","dog","has","flea"}));
		trainData.add(Arrays.asList(new String[]{"fuck","you"}));
		trainData.add(Arrays.asList(new String[]{"hello","world"}));
		trainData.add(Arrays.asList(new String[]{"you","are","an","ass"}));
		trainData.add(Arrays.asList(new String[]{"don't","touch","him","asshole"}));
		trainData.add(Arrays.asList(new String[]{"take","him","for","a","walk"}));
		trainData.add(Arrays.asList(new String[]{"suck","my","dick"}));
		trainData.add(Arrays.asList(new String[]{"thank","god","you","are","alright"}));
		trainDataClass.add(0);
		trainDataClass.add(1);
		trainDataClass.add(0);
		trainDataClass.add(1);
		trainDataClass.add(1);
		trainDataClass.add(0);
		trainDataClass.add(1);
		trainDataClass.add(0);
	}
	
	public void print(){
		for(int i = 0;i<trainDataClass.size();i++){
			System.out.println(trainData.get(i)+trainDataClass.get(i).toString());
		}
	}
	
	public void addEntry(int dataClass,String... data){
		trainDataClass.add(dataClass);
		List<String> mdata = new ArrayList<String>();
		for(String word:data){
			mdata.add(word);
		}
		trainData.add(mdata);
	}
	
	public List<List<String>> getData(){
		return trainData;
	}
	
	public List<Integer> getDataClass(){
		return trainDataClass;
	}
}
