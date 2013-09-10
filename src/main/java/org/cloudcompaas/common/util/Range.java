/*******************************************************************************
 * Copyright (c) 2013, Andrés García García All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * (1) Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 
 * (2) Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * (3) Neither the name of the Universitat Politècnica de València nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package org.cloudcompaas.common.util;

import es.upv.grycap.cloudcompaas.BoundaryType;
import es.upv.grycap.cloudcompaas.ExactType;
import es.upv.grycap.cloudcompaas.RangeType;
import es.upv.grycap.cloudcompaas.RangeValueType;

/**
 * @author angarg12
 *
 */
public class Range {
	private RangeValueType range;
	
	public Range(RangeValueType object){
		range = object;
	}
	
	/*
	public Integer[] getExact(){
		if(range.getExactArray() == null) return null;
		Integer[] result = new Integer[range.getExactArray().length];
		ExactType[] exacts = range.getExactArray();
		for(int i = 0; i < exacts.length; i++){
			result[i] = exacts[i].getBigIntegerValue().intValue();
		}
		return result;
	}
	*/
	
	/*
	public Integer[] getMinimum(){
		Collection<Integer> mins = new Vector<Integer>();
		
		ExactType[] exacts = range.getExactArray();
		if(exacts != null){
			for(int i = 0; i < exacts.length; i++){
				if(exacts[i].getEpsilon() != null){
					mins.add(exacts[i].getBigIntegerValue().subtract(exacts[i].getEpsilon()).intValue());
				}
			}
		}
		
		BoundaryType lbr = range.getLowerBoundedRange();
		if(lbr != null){
			if(lbr.getExclusiveBound() == true){
				mins.add(lbr.getBigIntegerValue().add(new BigInteger("1")).intValue());
			}else{
				mins.add(lbr.getBigIntegerValue().intValue());
			}
		}
		
		RangeType[] ranges = range.getRangeArray();
		if(ranges != null){
			for(int i = 0; i < ranges.length; i++){
				if(ranges[i].getLowerBound().getExclusiveBound() == true){
					mins.add(ranges[i].getLowerBound().getBigIntegerValue().add(new BigInteger("1")).intValue());
				}else{
					mins.add(ranges[i].getLowerBound().getBigIntegerValue().intValue());
				}
			}
		}

		return (Integer[]) mins.toArray();
	}
	*/
	
	/*
	 public Integer[] getMaximum(){
		Collection<Integer> maxs = new Vector<Integer>();
		
		ExactType[] exacts = range.getExactArray();
		if(exacts != null){
			for(int i = 0; i < exacts.length; i++){
				if(exacts[i].getEpsilon() != null){
					maxs.add(exacts[i].getBigIntegerValue().add(exacts[i].getEpsilon()).intValue());
				}
			}
		}
		
		BoundaryType ubr = range.getUpperBoundedRange();
		if(ubr != null){
			if(ubr.getExclusiveBound() == true){
				maxs.add(ubr.getBigIntegerValue().subtract(new BigInteger("1")).intValue());
			}else{
				maxs.add(ubr.getBigIntegerValue().intValue());
			}
		}
		
		RangeType[] ranges = range.getRangeArray();
		if(ranges != null){
			for(int i = 0; i < ranges.length; i++){
				if(ranges[i].getUpperBound().getExclusiveBound() == true){
					maxs.add(ranges[i].getLowerBound().getBigIntegerValue().subtract(new BigInteger("1")).intValue());
				}else{
					maxs.add(ranges[i].getLowerBound().getBigIntegerValue().intValue());
				}
			}
		}

		return (Integer[]) maxs.toArray();
	}
	*/
	
	public boolean isInRange(int value) throws Exception {
		ExactType[] exacts = range.getExactArray();
		BoundaryType lbr = range.getLowerBoundedRange();
		BoundaryType ubr = range.getUpperBoundedRange();
		RangeType[] ranges = range.getRangeArray();
		
		if(exacts == null && lbr == null && ubr == null && ranges == null){
			throw new Exception("No value has been defined for Range.");
		}
		
		if(exacts != null){
			for(int i = 0; i < exacts.length; i++){
				if(exacts[i].getBigIntegerValue().intValue() == value) return true;
				if(exacts[i].isSetEpsilon() == true){
					long min = exacts[i].getBigIntegerValue().intValue()-exacts[i].getEpsilon();
					long max = exacts[i].getBigIntegerValue().intValue()+exacts[i].getEpsilon();
					if(value >= min && value <= max){
						return true;
					}
				}
			}
		}
		
		if(lbr != null){
			int min = lbr.getBigIntegerValue().intValue();
			if(lbr.getExclusiveBound() == true){
				min++;
			}
			if(value >= min){
				return true;
			}
		}
		
		if(ubr != null){
			int max = ubr.getBigIntegerValue().intValue();
			if(ubr.getExclusiveBound() == true){
				max--;
			}
			if(value <= max){
				return true;
			}
		}
		
		if(ranges != null){
			for(int i = 0; i < ranges.length; i++){
				int min = ranges[i].getLowerBound().getBigIntegerValue().intValue();
				int max = ranges[i].getUpperBound().getBigIntegerValue().intValue();
				if(ranges[i].getLowerBound().getExclusiveBound() == true){
					min++;
				}
				if(ranges[i].getUpperBound().getExclusiveBound() == true){
					max--;
				}
				if(max > min){
					throw new Exception("PhysicalResource value malformed: upper and lower bounds are exclusive.");
				}
				if(value >= min && value <= max){
					return true;
				}
			}
		}

		return false;
	}
}
