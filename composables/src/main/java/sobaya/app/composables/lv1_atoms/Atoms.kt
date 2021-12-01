package sobaya.app.composables.lv1_atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import sobaya.app.composables.R

@Composable
fun CircleImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                transformations(CircleCropTransformation())
            }
        ),
        contentDescription = null,
        modifier = Modifier
            .size(
                dimensionResource(id = R.dimen.circle_image_size)
            )
    )
}
