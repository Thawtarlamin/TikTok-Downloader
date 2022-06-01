# TikTok-Downloader
> TikTok က video များအား logo or Water-marks မပါပဲ‌‌ဒေါင်းနိုင်အောင်ရည်ရွယ်၍ ထုတ်ပေးထားပါသည်။
# Usage Library
>settings.gradle ထည့်‌ပေးပါ

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
>build.gradle ထည့်‌ပေးပါ
```
dependencies {
	        implementation 'com.github.Thawtarlamin:TikTok-Downloader:1.3.0'
	}
```

>MainActivity.java
```
new GetTikTokData(this, "https://www.tiktok.com/@lhakpasherpa5/video/7085729188493151515", new GetTikTokData.onComplete() {
            @Override
            public void onComplete(TikTokModels models) {
                Log.d("my-test", "No Water: "+models.getNo_Water());
                Log.d("my-test", "Water: "+models.getWater());
                Log.d("my-test", "Thumb: "+models.getAvatarThumb());
                Log.d("my-test", "Name: "+models.getNickname());
                Log.d("my-test", "Audio: "+models.getAudio_url());
                Log.d("my-test", "Duration: "+models.getDuration());
            }
        }, new GetTikTokData.onError() {
            @Override
            public void onError(String onError) {

            }
        });
```

> အသုံးပြုသူများအဆ‌င်ပြေစေရန်ရည်ရွယ်၍ ထုတ်ပေးထားခြင်းဖြစ်ပါသည်။

# Creator By Thaw Tar La Minn


# 😘😘😘😘
# Donate Paypal
```
nyeineikhin.nek@gmail.com
```

>Thank you for  All User
