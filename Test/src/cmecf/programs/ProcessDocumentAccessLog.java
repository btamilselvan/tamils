package cmecf.programs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessDocumentAccessLog {

	private static void extractDocDlsIds() {
		try {

			Supplier<Stream<String>> ss = () -> getStream();

			Map<String, Long> countMap = ss.get().filter(ProcessDocumentAccessLog::filterByDate)
					.map(ProcessDocumentAccessLog::processLine)
					.collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));

			Map<String, List<String>> dateMap = ss.get().filter(ProcessDocumentAccessLog::filterByDate)
					.map(ProcessDocumentAccessLog::processLineAsArr).collect(Collectors.groupingBy(arr -> arr[0],
							Collectors.mapping(arr -> arr[1], Collectors.toList())));

			countMap.entrySet().stream().filter(e -> e.getValue() == 1)
					.forEach(e -> System.out.println(e.getKey() + " " + e.getValue() + " " + dateMap.get(e.getKey())));

//			stream.forEach(ProcessDocumentAccessLog::processLine);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Stream<String> getStream() {
		try {
			return Files.lines(Paths.get("C:\\Tamil\\temp\\document_access.live.log"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String processLine(String line) {
		return line.split("\\|")[1];
	}

	private static String[] processLineAsArr(String line) {
		return new String[] { line.split("\\|")[1], line.split("\\|")[0] };
	}

	private static boolean filterByDate(String line) {
		return (line.split("\\|")[0].contains("12/9/2020 12:0") || line.split("\\|")[0].contains("12/9/2020 12:1") || line.split("\\|")[0].contains("12/9/2020 12:2"));
	}

	public static void main(String[] args){
		extractDocDlsIds();
	}
}
