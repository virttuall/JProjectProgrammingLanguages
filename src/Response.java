
public class Response {
	
	String wrongCode;
	String recomendation;
	String solution;
	
	
	public Response(String wrongCode, String recomendation, String solution) {
		super();
		this.wrongCode = wrongCode;
		this.recomendation = recomendation;
		this.solution = solution;
	}
	public String getWrongCode() {
		return wrongCode;
	}
	public void setWrongCode(String wrongCode) {
		this.wrongCode = wrongCode;
	}
	public String getRecomendation() {
		return recomendation;
	}
	public void setRecomendation(String recomendation) {
		this.recomendation = recomendation;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	@Override
	public String toString() {
		return "Response [wrongCode=" + wrongCode + ", recomendation=" + recomendation + ", solution=" + solution + "]";
	}
	
	
	
}
