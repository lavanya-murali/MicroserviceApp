package com.sample.app.sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sample.app.rest.Data;

@Component
public class SequenceSelector {
	
	public Data swap(String swapType, String swapFunction, Data data){
		String firstListofNumbers = data.getString1();
		String secondListofNumbers = data.getString2();
		List<Integer> firstList = firstListofNumbers!=null && firstListofNumbers.trim().length()>0 ? Arrays.asList(
				firstListofNumbers.split(",")).stream().map(e -> new Integer(e)).collect(
						Collectors.toList()) : null;
		List<Integer> secondList = secondListofNumbers!=null && secondListofNumbers.trim().length()>0 ? Arrays.asList(
				secondListofNumbers.split(",")).stream().map(e -> new Integer(e)).collect(
						Collectors.toList()) :null;
		List<Integer> indexes  = firstList !=null && secondList!=null? generateSequence(
				swapType, swapFunction, firstList, secondList) :null;
		if (indexes!=null)
		{
			indexes.forEach(p-> {
					int i = p.intValue();
					Integer first = firstList.get(i);
					firstList.set(firstList.indexOf(firstList.get(i)), secondList.get(i));
					secondList.set(secondList.indexOf(secondList.get(i)), first);
			});
		}
		
		data.setString1(firstList.toString());
		data.setString2(secondList.toString());
		return data;
		
	}
	
	private List<Integer> generateSequence(String swapType, String swapFunction, List<Integer> firstList, List<Integer> secondList){
		
		SwapFunctionEnum function = SwapFunctionEnum.valueOf(swapFunction);
		SwapTypeEnum type = SwapTypeEnum.getByType(swapType);
		switch (type) {
		case BY_POSITION:
			return getSwapIndexes(function, firstList, secondList);
		case BY_VALUE:
			return createSwapIndexes(function, firstList);
		default:
			return null;

		}
	}

	private List<Integer> createSwapIndexes(SwapFunctionEnum function, List<Integer> firstList) {
		List<Integer> indexes = new ArrayList<Integer>();
		firstList.forEach(p -> {
			if (function.compareTo(SwapFunctionEnum.EVEN) == 0 && p%2 == 0) 
				indexes.add(firstList.indexOf(p));
			else if (function.compareTo(SwapFunctionEnum.ODD) == 0 && (p==1 || p%2 != 0)) 
				indexes.add(firstList.indexOf(p));
		});
		
		return indexes;
	}

	private List<Integer> getSwapIndexes(SwapFunctionEnum function, List<Integer> firstList, List<Integer> secondList) {
		int start = function.getStart();
		int skip = function.getSkip()+1;
		
		List<Integer> indexes = new ArrayList<Integer>();
		int size = firstList.size()>secondList.size()?secondList.size():firstList.size();
		
		for(int i=start;i<size;i+=skip)
		{
			indexes.add(i);
		}
		return indexes;
	}

	public static void main(String[] args) {
		SequenceSelector selector = new SequenceSelector();
		Data data = new Data();
		data.setString1("1,2,3,4,5");
		data.setString2("10,20,30,40,50");
		selector.swap("value", "ODD", data);
	}
}
