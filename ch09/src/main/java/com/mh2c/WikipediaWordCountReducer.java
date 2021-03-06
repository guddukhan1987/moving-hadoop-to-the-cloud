/*
Copyright 2016 William A. Havanki, Jr.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.mh2c;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * Reducer that sums up word counts.
 */
public class WikipediaWordCountReducer extends MapReduceBase
  implements Reducer<Text, IntWritable, Text, IntWritable> {

  private IntWritable sumIntWritable = new IntWritable();

  /**
   * key = word
   * values = counts
   */
  @Override
  public void reduce(Text key, Iterator<IntWritable> values,
                     OutputCollector<Text, IntWritable> output, Reporter reporter)
    throws IOException {

    // Total up the incoming counts for the word
    int sum = 0;
    while (values.hasNext()) {
      sum += values.next().get();
    }
    // Emit the word count
    sumIntWritable.set(sum);
    output.collect(key, sumIntWritable);
  }
}
