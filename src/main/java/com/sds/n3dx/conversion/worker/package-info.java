/**
 * directory root는 configmap을 통해서 전달 받음
 * Application이 올라오면서
 * 
 * 1. 특정 카프라 Transport 및 Listener를 초기화 함 - configmap을 통해서 관련 정보를 받음
 * 2. 완전히 구동되면 카프카 트랜스포트를 통해서 worker 상태 정보를 전송함 (created) 
 *    - 전송 큐 정보는 configmap을 (또는 application.yaml) 통해서 전달 방음
 * 3. 카프카 수신 큐를 통해서 변환요청 정보를 수신함
 *    - 전송 큐를 통해서 Workload 상태갱신 정보를 전송함
 * 4. 변환요청 정보를 받아서 변환을 수행함
 *    - 변환 중간 중간에 Workload 상태 갱신 정보를 전송함
 * 5. 변환이 완료되면 변환 완료 정보를 전송 큐를 통해서 전송함
 *    - 전송후에 시스템을 종료함
 *    - JOB 제거여부를 확인해야 함 (스스로 제거되지 않는다면 서버에서 제거애 주어야 함)  
 */
package com.sds.n3dx.conversion.worker;