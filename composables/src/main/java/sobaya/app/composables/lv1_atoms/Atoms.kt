package sobaya.app.composables.lv1_atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.ImageRequest.Builder
import coil.transform.CircleCropTransformation
import sobaya.app.composables.R

@Composable
fun CircleImage(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(
            Builder(LocalContext.current).data(data = imageUrl).apply(block = fun Builder.() {
                transformations(CircleCropTransformation())
            }).build()
        ),
        contentDescription = null,
        modifier = Modifier
            .size(
                dimensionResource(id = R.dimen.circle_image_size)
            )
    )
}
