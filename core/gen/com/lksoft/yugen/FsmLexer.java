// Generated from E:/Development/yugen/core/src/main/antlr\Fsm.g4 by ANTLR 4.5.3
package com.lksoft.yugen;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FsmLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, WS=22, COMMENT=23, LINE_COMMENT=24, 
		ID=25, INT=26, FLOAT=27, STRING=28;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "WS", "COMMENT", "LINE_COMMENT", "ID", 
		"INT", "FLOAT", "STRING"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'Params'", "'Stateless'", "'State'", "'('", "')'", "'['", "']'", 
		"'Trigger'", "':'", "'->'", "'='", "'>='", "'<='", "'<'", "'>'", "'=='", 
		"'!='", "'&&'", "'||'", "'!'", "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, "WS", "COMMENT", 
		"LINE_COMMENT", "ID", "INT", "FLOAT", "STRING"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public FsmLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Fsm.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\36\u00c6\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n"+
		"\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25"+
		"\3\26\3\26\3\27\6\27\u0085\n\27\r\27\16\27\u0086\3\27\3\27\3\30\3\30\3"+
		"\30\3\30\7\30\u008f\n\30\f\30\16\30\u0092\13\30\3\30\3\30\3\30\3\30\3"+
		"\30\3\31\3\31\3\31\3\31\7\31\u009d\n\31\f\31\16\31\u00a0\13\31\3\31\3"+
		"\31\3\32\3\32\7\32\u00a6\n\32\f\32\16\32\u00a9\13\32\3\33\5\33\u00ac\n"+
		"\33\3\33\6\33\u00af\n\33\r\33\16\33\u00b0\3\34\5\34\u00b4\n\34\3\34\3"+
		"\34\6\34\u00b8\n\34\r\34\16\34\u00b9\3\35\3\35\3\35\3\35\7\35\u00c0\n"+
		"\35\f\35\16\35\u00c3\13\35\3\35\3\35\3\u0090\2\36\3\3\5\4\7\5\t\6\13\7"+
		"\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25"+
		")\26+\27-\30/\31\61\32\63\33\65\34\67\359\36\3\2\b\5\2\13\f\16\17\"\""+
		"\4\2\f\f\17\17\6\2&&C\\aac|\b\2&&\60\60\62;C\\aac|\3\2\62;\3\2$$\u00cf"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\3;\3"+
		"\2\2\2\5B\3\2\2\2\7L\3\2\2\2\tR\3\2\2\2\13T\3\2\2\2\rV\3\2\2\2\17X\3\2"+
		"\2\2\21Z\3\2\2\2\23b\3\2\2\2\25d\3\2\2\2\27g\3\2\2\2\31i\3\2\2\2\33l\3"+
		"\2\2\2\35o\3\2\2\2\37q\3\2\2\2!s\3\2\2\2#v\3\2\2\2%y\3\2\2\2\'|\3\2\2"+
		"\2)\177\3\2\2\2+\u0081\3\2\2\2-\u0084\3\2\2\2/\u008a\3\2\2\2\61\u0098"+
		"\3\2\2\2\63\u00a3\3\2\2\2\65\u00ab\3\2\2\2\67\u00b3\3\2\2\29\u00bb\3\2"+
		"\2\2;<\7R\2\2<=\7c\2\2=>\7t\2\2>?\7c\2\2?@\7o\2\2@A\7u\2\2A\4\3\2\2\2"+
		"BC\7U\2\2CD\7v\2\2DE\7c\2\2EF\7v\2\2FG\7g\2\2GH\7n\2\2HI\7g\2\2IJ\7u\2"+
		"\2JK\7u\2\2K\6\3\2\2\2LM\7U\2\2MN\7v\2\2NO\7c\2\2OP\7v\2\2PQ\7g\2\2Q\b"+
		"\3\2\2\2RS\7*\2\2S\n\3\2\2\2TU\7+\2\2U\f\3\2\2\2VW\7]\2\2W\16\3\2\2\2"+
		"XY\7_\2\2Y\20\3\2\2\2Z[\7V\2\2[\\\7t\2\2\\]\7k\2\2]^\7i\2\2^_\7i\2\2_"+
		"`\7g\2\2`a\7t\2\2a\22\3\2\2\2bc\7<\2\2c\24\3\2\2\2de\7/\2\2ef\7@\2\2f"+
		"\26\3\2\2\2gh\7?\2\2h\30\3\2\2\2ij\7@\2\2jk\7?\2\2k\32\3\2\2\2lm\7>\2"+
		"\2mn\7?\2\2n\34\3\2\2\2op\7>\2\2p\36\3\2\2\2qr\7@\2\2r \3\2\2\2st\7?\2"+
		"\2tu\7?\2\2u\"\3\2\2\2vw\7#\2\2wx\7?\2\2x$\3\2\2\2yz\7(\2\2z{\7(\2\2{"+
		"&\3\2\2\2|}\7~\2\2}~\7~\2\2~(\3\2\2\2\177\u0080\7#\2\2\u0080*\3\2\2\2"+
		"\u0081\u0082\7.\2\2\u0082,\3\2\2\2\u0083\u0085\t\2\2\2\u0084\u0083\3\2"+
		"\2\2\u0085\u0086\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087"+
		"\u0088\3\2\2\2\u0088\u0089\b\27\2\2\u0089.\3\2\2\2\u008a\u008b\7\61\2"+
		"\2\u008b\u008c\7,\2\2\u008c\u0090\3\2\2\2\u008d\u008f\13\2\2\2\u008e\u008d"+
		"\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u0091\3\2\2\2\u0090\u008e\3\2\2\2\u0091"+
		"\u0093\3\2\2\2\u0092\u0090\3\2\2\2\u0093\u0094\7,\2\2\u0094\u0095\7\61"+
		"\2\2\u0095\u0096\3\2\2\2\u0096\u0097\b\30\2\2\u0097\60\3\2\2\2\u0098\u0099"+
		"\7\61\2\2\u0099\u009a\7\61\2\2\u009a\u009e\3\2\2\2\u009b\u009d\n\3\2\2"+
		"\u009c\u009b\3\2\2\2\u009d\u00a0\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f"+
		"\3\2\2\2\u009f\u00a1\3\2\2\2\u00a0\u009e\3\2\2\2\u00a1\u00a2\b\31\2\2"+
		"\u00a2\62\3\2\2\2\u00a3\u00a7\t\4\2\2\u00a4\u00a6\t\5\2\2\u00a5\u00a4"+
		"\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8"+
		"\64\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ac\7/\2\2\u00ab\u00aa\3\2\2\2"+
		"\u00ab\u00ac\3\2\2\2\u00ac\u00ae\3\2\2\2\u00ad\u00af\t\6\2\2\u00ae\u00ad"+
		"\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1"+
		"\66\3\2\2\2\u00b2\u00b4\5\65\33\2\u00b3\u00b2\3\2\2\2\u00b3\u00b4\3\2"+
		"\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b7\7\60\2\2\u00b6\u00b8\t\6\2\2\u00b7"+
		"\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9\u00ba\3\2"+
		"\2\2\u00ba8\3\2\2\2\u00bb\u00c1\7$\2\2\u00bc\u00bd\7^\2\2\u00bd\u00c0"+
		"\7$\2\2\u00be\u00c0\n\7\2\2\u00bf\u00bc\3\2\2\2\u00bf\u00be\3\2\2\2\u00c0"+
		"\u00c3\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c4\3\2"+
		"\2\2\u00c3\u00c1\3\2\2\2\u00c4\u00c5\7$\2\2\u00c5:\3\2\2\2\r\2\u0086\u0090"+
		"\u009e\u00a7\u00ab\u00b0\u00b3\u00b9\u00bf\u00c1\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}