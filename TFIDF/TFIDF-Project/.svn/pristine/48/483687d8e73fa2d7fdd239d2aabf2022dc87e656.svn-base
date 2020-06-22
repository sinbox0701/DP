package hufs.ces.text;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class StreamTokenizer implements Tokenizer {

	static final String delimPattern = "(\\s|\\p{Punct})+";
	static Set<String> filterSet = null;
	static {
		try {
			URI stopWordURI = StreamTokenizer.class.getResource("/english.txt").toURI();
			filterSet = Files.lines(Paths.get(stopWordURI),Charset.defaultCharset())
					.map(s->s.trim()).map(s->s.toLowerCase()).collect(Collectors.toSet());
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		System.out.println("filterSet="+filterSet);
	}

	@Override
	public List<String> getTokens(String document) {

		List<String> res = Arrays.stream(document.split(delimPattern))
		.map(s->s.trim())
		.filter(s->s.matches("\\p{Alpha}+"))
		.map(s->s.toLowerCase())
		.filter(s->!filterSet.contains(s))
		.collect(Collectors.toList());
		return res;
	}

	public static void main(String[] args) {
		String text = null;
		try {
			URI textURI = StreamTokenizer.class.getResource("/test/poe-raven.txt").toURI();
			text = Files.lines(Paths.get(textURI),Charset.defaultCharset())
					.collect(Collectors.joining(","));

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		System.out.println(text.length()+":"+text);
		List<String> tokens = new StreamTokenizer().getTokens(text);
		System.out.println(tokens.size()+":"+tokens);

	}
}
