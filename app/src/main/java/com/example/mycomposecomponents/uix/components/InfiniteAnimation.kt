package com.example.mycomposecomponents.uix.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mycomposecomponents.R

@Composable
fun InfiniteAnimation() {
    // 'rememberInfiniteTransition' ile sonsuz bir animasyon oluşturuyoruz.
    // Bu animasyon bileşeni, animasyonun sürekli tekrar etmesini sağlar.
    val transition = rememberInfiniteTransition(
        label = "my_transition" // Bu, animasyonun ayırt edilmesi için kullanılabilecek bir etiket.
    )

    // 'animateFloat' fonksiyonu ile 0f'dan 1f'a kadar bir float değeri animasyonu yapıyoruz.
    // Bu animasyon sonsuz tekrar modunda olacak ve ileri geri hareket edecek.
    val ratio by transition.animateFloat(
        initialValue = 0f, // Başlangıç değeri 0 (başlangıç boyutu).
        targetValue = 1f,  // Hedef değer 1 (tam boyuta ulaşma).
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000), // Animasyonun 1 saniye içinde tamamlanmasını sağlayan zamanlama fonksiyonu.
            repeatMode = RepeatMode.Reverse // Animasyon ileri geri (ters) şekilde tekrarlanacak.
        ),
        label = "float_animation" // Animasyonun adı, Debug için kullanılabilir.
    )

    // 'Box' bileşeni ile bir konteyner oluşturuyoruz, bu konteyner içinde animasyonu uygulayacağız.
    Box(
        modifier = Modifier
            .size(300.dp) // Box'ın başlangıç boyutu 300x300 dp olarak ayarlanıyor.
            .graphicsLayer {
                // Box'un X ve Y eksenlerinde ölçeklendirilmesi (büyüyüp küçülmesi) için animasyonlu değerleri kullanıyoruz.
                scaleX = ratio // X ekseninde genişliği, animasyonun ratio değeri ile kontrol ediyoruz.
                scaleY = ratio // Y ekseninde yüksekliği, animasyonun ratio değeri ile kontrol ediyoruz.
            }
    ) {
        // 'Image' bileşeni ile drawable'dan bir resim gösteriyoruz.
        // Burada R.drawable.ic_android resmini kullanıyoruz.
        Image(
            painter = painterResource(id = R.drawable.ic_android), // Resmi 'painterResource' ile alıyoruz.
            contentDescription = "", // İçerik açıklaması (accessibility) için. Eğer bu bir kullanıcı arayüzü tasarımıysa açıklama eklemek önemli olabilir.
            modifier = Modifier.size(300.dp) // Resmin boyutu, Box ile aynı olacak şekilde 300x300 dp ayarlandı.
        )
    }
}