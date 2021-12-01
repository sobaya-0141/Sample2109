package sobaya.app.composables.lv3_organisms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import sobaya.app.composables.R
import sobaya.app.composables.lv1_atoms.CircleImage

@Composable
fun ListCard(onClick: (login: String) -> Unit, login: String) {
    Card(
        elevation = dimensionResource(id = R.dimen.card_list_elevation),
        backgroundColor = Color.LightGray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = dimensionResource(id = R.dimen.card_list_bottom_padding)
            )
            .clickable {
                onClick(login)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.list_row_padding)
                )
        ) {
            CircleImage(imageUrl = "https://avatars.githubusercontent.com/u/45986582?v=4")
            Column {
                Text(login)
                Text(login)
            }
        }
    }
}
