# Roots
> Flow Week2 4분반 8팀 (박상빈, 장지원) 


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
![Screenshot_20220713_204644](https://user-images.githubusercontent.com/83392219/178729081-2c756145-6167-442a-a678-f3830d46728f.png)

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
![Collage Maker-13-Jul-2022-09 13-PM](https://user-images.githubusercontent.com/83392219/178730794-ec049fb6-d981-4abc-bd51-af2b5040b14f.jpg)

![Collage Maker-13-Jul-2022-09 21-PM](https://user-images.githubusercontent.com/83392219/178732215-f60547e0-ec2e-4402-b42c-a34088e9fd26.jpg)


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
![tab3_ranking](https://user-images.githubusercontent.com/83392219/179447068-cd8bf7b1-5b41-4d4e-bce4-e88d4f1667cb.png)
  
#### Major features   
*** 


### TAB 4 - 공통 기능 
#### 노래 검색 ####
![Collage Maker-18-Jul-2022-01 52-PM](https://user-images.githubusercontent.com/83392219/179447296-80f1cd74-130e-41ab-8973-c44dc0869eed.jpg)
1. 검색 기능을 통해 원하는 노래를 찾을 수 있습니다.
2. 노래 제목을 다 입력하지 않아도 자동 완성으로 노래를 찾아 알파벳순으로 정렬하여 보여줍니다.


#### 댓글 달기 ####
![Collage Maker-18-Jul-2022-01 58-PM](https://user-images.githubusercontent.com/83392219/179447829-4df8738f-fa35-4de7-925e-84ac4417b5c9.jpg)
1. 로그인을 한 상태이면 댓글을 달 수 있습니다.
2. 로그인을 하지 않았다면 로그인을 하라는 메세지가 뜹니다.
3. 댓글을 달면 데이터베이스에 저장됩니다.
4. 이때까지 썼던 댓글들은 데이터베이스에서 가져오고, 이를 화면에 리사이클러뷰로 보여줍니다.


#### 로그인 #### 
![Collage Maker-18-Jul-2022-02 15-PM](https://user-images.githubusercontent.com/83392219/179449081-914cc547-1358-4512-a111-80c1339f7e9b.jpg)
1. 카카오톡 로그인 API를 사용하여 로그인 기능을 구현했습니다.
2. 실제 카카오톡 계정에서 프로필과 이름 정보를 가져왔습니다.

#### 노래 10초 듣기 기능 ####


#### 광고 기능 ####
