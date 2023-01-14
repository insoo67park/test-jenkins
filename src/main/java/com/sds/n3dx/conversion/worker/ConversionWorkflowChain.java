package com.sds.n3dx.conversion.worker;

/**
 * 도면 종류에 따라 psc에서 끝나는 경우, msc까지 가는 경우가 있음
 * 1. File별로 임시 디렉토리에 symbolic link 생성
 * 2. part sc 변환, 변환후 symbolic link 삭제
 * 3. 변환시 생성한 assembly tree로 각종정보 parsing
 * 4. 원본 파일별 최신 revision의 symbolic link 생성
 * 5. thumbnail & master xml  생성
 * 6. 원본 파일구조대로 최신 revision의 part sc에 대한 symbolic link 생성
 * 7. master xml과 part sc symbolic link 사용하여 master sc 생성
 * 
 * 3번까지 가능 경우와 7번까지 가능 경우가 있음
 * 하나의 프로젝트에 많은 파일이 천개 정도로 많을 수도 있음 (고려 해야 함)
 * 
 * @author insoo67.park
 *
 */
public class ConversionWorkflowChain {
	
	private ConversionActivity next=null;

	public void doChain(ConversionRequest req, ConversionResponse res) {
		if(next != null) {
			next.execute(req, res);
		}
	}
	
	public void setNext(ConversionActivity next) {
		this.next=next;
	}
}
