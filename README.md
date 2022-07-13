# Roots
> Flow Week2 4분반 8팀 (박상빈, 장지원) 


* 다양한 음악을 들을 수 있게 해주는 Android 기반 어플리케이션입니다.  
* 플레이 리스트로 좋아하는 음악을 저장하고 관리할 수 있습니다.  
* 랜덤으로 음악을 추천받을 수 있습니다.  

![MainPage](https://user-images.githubusercontent.com/63276842/148056550-1f43af05-117b-4e8d-b836-e8171faa55b6.png)  
***

### A. 개발 팀원  
* KAIST 전산학부 [박상빈](https://github.com/sbpark0611)  
* 부산대  [장지원](https://github.com/DebbieIsFree)  
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
* Recycler View를 이용하여 저장된 인물정보를 보여준다. 보여줄 인물이 많을 경우 넘어가 보이지 않는 view를 재사용한다.  
* 연락처를 저장할 때에는 사용자로부터 받은 데이터를 json file 형식으로 변환하여 local storage에 저장한다.  
* 앱 실행시 기존에 저장해두었던 jsonfile을 로드하여 인물정보를 보여준다.  
* 인물 정보를 저장할 때 도로명 주소를 받아오기 위해 다음 도로명 주소 api를 이용한다.  
***

### TAB 2 - Recommendation 
![Collage Maker-13-Jul-2022-09 13-PM](https://user-images.githubusercontent.com/83392219/178730794-ec049fb6-d981-4abc-bd51-af2b5040b14f.jpg)

![Collage Maker-13-Jul-2022-09 21-PM](https://user-images.githubusercontent.com/83392219/178732215-f60547e0-ec2e-4402-b42c-a34088e9fd26.jpg)


#### Major features   
* 사용자에게 무작위로 음악 5곡을 추천해줍니다.
 * 난수가 중복되지 않도록 코드를 작성하여 노래가 겹치지 않도록 추천해줍니다.
 * 좌,우로 스와이프하여 추천 노래를 구경할 수 있습니다.  
 * 노래 제목, 가수 이름, 앨범 이미지를 누르면 해당 곡을 바로 들을 수 있는 페이지로 이동합니다.

#### 기술 설명  
1. `Viewpager2`와 `dotIndicator`를 활용하여, 좌/우로 스와이프가 가능한 티켓 갤러리를 제작했습니다.  
#### [ViewPager2 샘플 코드](https://github.com/android/views-widgets-samples/tree/master/ViewPager2)  
2. Front와 Back으로 나눈 후 animation를 활용하여 Card가 Flip되는 효과를 구현했습니다.  

### TAB 3 - Ranking  
![page3](https://user-images.githubusercontent.com/63276842/148057789-49047c7d-5829-42c5-9ce2-1221ff2f84b6.png)  

#### Major features   
* 홈파티 이름, 일정, 참가자 등의 정보를 작성하여 티켓을 생성할 수 있습니다.  
* 이미 만들어진 티켓의 바코드를 스캔하여 데이터를 저장할 수 있습니다.  
***

#### 기술 설명  
1. 티켓 생성  
* 사용자가 파티 정보를 하나씩 입력할 때마다 마치 티켓이 인쇄되는 듯한 효과를 구현하였습니다.  
* 사용자 입력에 대한 반응이 시각적으로 전달되도록 [ObjectAnimator](https://developer.android.com/reference/android/animation/ObjectAnimator) 클래스를 사용하였습니다.  
* 예를 들어, 사용자가 선택한 색상이 크게 강조되는 효과가 다음과 같이 구현되었습니다.  
* 또한, 입력의 변화가 일어날 때마다 결과가 즉시 보여지도록 Custom EventListener를 사용하여 직접 애니메이션 효과를 구현하기도 했습니다.  
* 입력 페이지가 넘어갈 때 마다 티켓이 올라오는 효과는 ViewPager.OnPageChangeListener를 오버라이드하여 구현되었습니다.  

2. 티켓 불러오기  
* [ZXing](https://github.com/journeyapps/zxing-android-embedded) 라이브러리를 이용하여 각 티켓의 정보를 담고 있는 바코드를 생성하고 스캔하는 기능을 구현하였습니다. 

### TAB 4 - 공통 기능  
