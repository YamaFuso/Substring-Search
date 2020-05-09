**Read Me File To Performance Experiment Results**

1. Condition 1 uses original text to find a pattern, so it is a guaranteed find.
2. Condition 2 uses random generator to find a pattern, so it is highly possible that no substring could be found.
3. Experiment runs with four substring finding algorithm implementations: Brute Force 1 (Student Impl), Brute Force 2(Online Impl), KMP 1(Book Impl), KMP 2(Online Impl). 
4. There are two minor bugs in the program for counting inspect numbers for brute force algorithm. Developer forgot to reset initial value of counter to 0 in the constructor, so counters are started from -1(which was the default value for debugging). So, when you are examining the number of inspections for brute forces, remember that the actual number should be one more than recorded.
5. In the experiment, there are three types of dictionaries being used. 1. a to z (Ascii 97 - Ascii 122). 2. binary (Ascii ?). 3. DNA (Ascii ?).
6. Experiment Setup: In the whole experiment, there are 24 types of experiments, and each one is runned three times.
	- 2[CONDITIONS] * 3[TYPES OF DICTIONARY] * 4[ALGORITHM] * 3[REPEATED TIMES] = 72 [TOTAL EXPERIMENT RECORDS]

	- The experiment is runned by the following java code:
		
		for (int i = 0; i < 3; i++) {
			doSearch(1024, 4194304, "binary", i);
			doSearch(1024, 4194304, "DNA", i);
			doSearch(1024, 4194304, "a-z", i);
		}

	- void doSearch(int startIndex, int endIndex, String mode, int times){
		
		// Loop For Randomize TEXT
		for (int N = startIndex; N <= endIndex; N*=2) {
			String txt = Generater.generateRandomText(mode, N);
			
			// Loop For Randomize PATTERN
			for (int i = startIndex; i <= N; i*=2) {
				String pattern = Generater.generateRandomText(mode, i);
					
				doSearchWithAlgo(txt, pattern, "BF1");
				doSearchWithAlgo(txt, pattern, "BF2");
				doSearchWithAlgo(txt, pattern, "KMP1");
				doSearchWithAlgo(txt, pattern, "KMP2");
			}
	}

7. Data Analysts are expected to derive theories about KMP Substring Finding Algorithm based on the observations of the performance experiment results. Also, Data Analysts are expected to find the advantage / disadvantage of KMP algorithm as a whole, and the specific advantage / disadvantage of specific KMP implementation.
8. Original experiment data is stored under Condition -> Original Files -> [Dictionary]. The postfix at the end indicates the trial number. 0 -> 1, 1 -> 2, 2 -> 3.
9. Experimenter has already formatted all the data into excel files for analyze. Those files are locating at Condition -> [File]
10. It is expected that the found index for each row, the [Found Index] from each algorithm should be the same.
11. One big disadvantage of KMP1 that might cause you unabling to run the program: The dfa 2D array needs [256][pattern.length] (for all ascii characters to have pattern.length states) -> when the pattern is extremely long, say 4194304 in the experiment, then you need 256 * 4194304 * 4 bytes to store the array. Converting that to GB, that is 4GB / 4096 MB [Which is really crazy]. Experimenter had to adjust many run configurations for JVM to run the program.



- For Data Analysts:
- Use the second and thrid trial to validate your guess, in case there were problems during the migration of data.
- Scroll over the row to compare a same "Searching" for "Same Pattern & Same Text", and use the observation under comparison to derive hypothesis.
- Try to find a mathematical formula that can account for the time and space complexity of the algorithms. Not just in Big-O, but specific approximation (like SPACE(KMP1) = 100N <- This is an example).
- After formed a hypothesis, try predict that under what circumstance will KMP be better, and more specifically, which KMP will be better in what sub-circumstances.
- Guesses from experimenter about the hypothesis: maybe there is a ratio relationship between pattern and text that guarantees KMP efficiency. For example, under [ Pattern.length / Text.length ] = 50%, KMP perform better. 
- Try to account for why the specific implementation has better performance / efficiency.
- You need to take constructor inspections and time into consideration as array inspection happened there also.