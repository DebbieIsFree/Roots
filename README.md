# Roots
> Flow Week2 -- 4분반 8팀 (박상빈, 장지원) 

* 다양한 음악을 들을 수 있게 해주는 Android 기반 어플리케이션입니다.  
* 플레이 리스트로 좋아하는 음악을 저장하고 관리할 수 있습니다.  
* 랜덤으로 음악을 추천받을 수 있습니다.  


### A. 개발 팀원  
* KAIST 전산학부 [박상빈](https://github.com/sbpark0611)  
* 부산대 정보컴퓨터공학과 [장지원](https://github.com/DebbieIsFree)  
***

### B. 개발 환경  
* OS: Android (minSdk: 21, targetSdk: 31)  
* Language: Java  
* IDE: Android Studio  
* Target Device: Galaxy S7  
***


### C. 어플리케이션 소개  
### TAB 1 - Home  
<img src="https://user-images.githubusercontent.com/83392219/179452336-12034973-5831-4752-9b8d-99e156ca2a96.jpg"  width="80%" height="80%"/>

![Collage Maker-18-Jul-2022-02 36-PM](https://user-images.githubusercontent.com/83392219/179450601-8c2b0a7c-4301-4421-a300-223c3adcdf86.jpg)

#### Major features   
* 새로운 플레이 리스트를 만들 수 있습니다.
  * 로그인을 하면 기존에 만들어 둔 플레이 리스트를 옆으로 스와이프 해서 볼 수 있습니다.
  * [플레이리스트 추가] 버튼을 누르면 새로운 플레이리스트를 만들 수 있습니다. 이때 로그인을 하지 않았으면 로그인을 하라는 알림이 뜹니다.
  * 개수에 상관없이 원하는 만큼 새로운 노래를 추가할 수 있습니다.
  * [삭제] 버튼을 누르면 해당 플레이 리스트를 삭제할 수 있습니다.
  * [시작] 버튼을 누르면 해당 플레이 리스트를 재생하는 화면으로 전환됩니다.  
  
#### 기술 설명  
***

### TAB 2 - Recommendation 
<img src="https://user-images.githubusercontent.com/83392219/178730794-ec049fb6-d981-4abc-bd51-af2b5040b14f.jpg"  width="80%" height="80%"/>

<img src="https://user-images.githubusercontent.com/83392219/178732215-f60547e0-ec2e-4402-b42c-a34088e9fd26.jpg"  width="80%" height="80%"/>

#### Major features   
* 사용자에게 무작위로 음악 5곡을 추천해줍니다.
 * 난수가 중복되지 않도록 코드를 작성하여 노래가 겹치지 않습니다.
 * 좌,우로 스와이프하여 추천 노래를 구경할 수 있습니다.  
 * 노래 제목, 가수 이름, 앨범 이미지를 누르면 해당 곡을 바로 들을 수 있는 페이지로 이동합니다.

#### 기술 설명  
1. `Viewpager2`와 `dotIndicator`를 활용하여, 좌/우로 스와이프가 가능한 티켓 갤러리를 제작했습니다.  
#### [ViewPager2 샘플 코드](https://github.com/android/views-widgets-samples/tree/master/ViewPager2)  
2. Front와 Back으로 나눈 후 animation를 활용하여 Card가 Flip되는 효과를 구현했습니다.  

### TAB 3 - Ranking  
<img src="https://user-images.githubusercontent.com/83392219/179447068-cd8bf7b1-5b41-4d4e-bce4-e88d4f1667cb.png"  width="370" height="600"/>  
  
#### Major features   
*** 


### TAB 4 - 공통 기능 
#### 노래 검색 ####
<img src="https://user-images.githubusercontent.com/83392219/179447296-80f1cd74-130e-41ab-8973-c44dc0869eed.jpg"  width="80%" height="80%"/>
1. 검색 기능을 통해 원하는 노래를 찾을 수 있습니다.
2. 노래 제목을 다 입력하지 않아도 자동 완성으로 노래를 찾아 알파벳순으로 정렬하여 보여줍니다.


#### 댓글 달기 ####
<img src="https://user-images.githubusercontent.com/83392219/179447829-4df8738f-fa35-4de7-925e-84ac4417b5c9.jpg"  width="80%" height="80%"/>
1. 로그인을 한 상태이면 댓글을 달 수 있습니다.
2. 로그인을 하지 않았다면 로그인을 하라는 메세지가 뜹니다.
3. 댓글을 달면 데이터베이스에 저장됩니다.
4. 이때까지 썼던 댓글들은 데이터베이스에서 가져오고, 이를 화면에 리사이클러뷰로 보여줍니다.


#### 로그인 #### 
![Collage Maker-18-Jul-2022-02 22-PM](https://user-images.githubusercontent.com/83392219/179449632-213df6d4-2f5e-4cc2-9841-7aa1bb1090fd.jpg)
1. 카카오톡 로그인 API를 사용하여 로그인 기능을 구현했습니다.
2. 실제 카카오톡 계정에서 프로필과 이름 정보를 가져왔습니다.

#### 노래 10초 듣기 기능 ####
<img src="https://user-images.githubusercontent.com/83392219/179450106-ff5d0d71-b0da-4344-9402-dcf77bb31282.png"  width="370" height="600"/>  
1. 로그인을 하지 않으면 노래 듣기가 10초로 제한됩니다.


#### 광고 기능 ####
<img src="https://user-images.githubusercontent.com/83392219/179450450-9a517998-ad41-4875-a247-f8d897b52c3a.jpg"  width="80%" height="80%"/>
1. 로그인을 하지 않으면 10초만 미리 듣기가 가능한데, 이때 광고를 시청하면 10초 듣기 제한이 없어집니다.


#### 좋아요 기능 ####
![Collage Maker-18-Jul-2022-02 54-PM](https://user-images.githubusercontent.com/83392219/179452179-39133854-544f-4116-8aee-e0e3fad0d486.jpg)
1. 노래를 듣다가 좋아요 라디오 버튼을 누르면 데이터베이스에 저장됩니다.
2. 로그인 후 프로필에 들어가서 [좋아요 목록 보기] 버튼을 누르면 이때까지 좋아요를 누른 노래를 확인할 수 있습니다.
3. 좋아요 목록에서 노래를 클릭하면 재생 화면으로 전환됩니다.

#### 서버 스트리밍 기술 #### 
서버에 저장된 음원 파일을 통쨰로 다운로드해 재생하는 것이 아니라 서버로부터 조금씩 다운로드해 실시간 스트리밍을 합니다.
