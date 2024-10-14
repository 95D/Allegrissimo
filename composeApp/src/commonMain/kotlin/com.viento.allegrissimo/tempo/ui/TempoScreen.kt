package com.viento.allegrissimo.tempo.ui

import allegrissimo.composeapp.generated.resources.Res
import allegrissimo.composeapp.generated.resources.bpm
import allegrissimo.composeapp.generated.resources.content_description_play
import allegrissimo.composeapp.generated.resources.content_description_stop
import allegrissimo.composeapp.generated.resources.icon_add
import allegrissimo.composeapp.generated.resources.icon_minus
import allegrissimo.composeapp.generated.resources.icon_play
import allegrissimo.composeapp.generated.resources.icon_stop
import allegrissimo.composeapp.generated.resources.tempo
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.viento.allegrissimo.tempo.TempoScreenViewModel
import com.viento.allegrissimo.tempo.model.Note
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun TempoRoute() {
    val tempoViewModel = viewModel(TempoScreenViewModel::class)
    val bpm by tempoViewModel.bpmStateFlow.collectAsState()
    val note by tempoViewModel.noteStateFlow.collectAsState()
    val noteCount by tempoViewModel.noteCountStateFlow.collectAsState()
    val isPlaying by tempoViewModel.isPlayingStateFlow.collectAsState()
    val currentIndex by tempoViewModel.currentNoteIndexStateFlow.collectAsState()

    TempoScreen(bpm, note, noteCount, currentIndex, isPlaying, tempoViewModel)
}

@Composable
fun TempoScreen(
    bpm: Int,
    note: Note,
    noteCount: Int,
    currentIndex: Int,
    isPlaying: Boolean,
    tempoViewModel: TempoScreenViewModel
) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        ControllerCard {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.tertiary,
                    text = stringResource(Res.string.bpm)
                )
                UpDownSwitch(
                    currentValue = bpm.toString(),
                    onClickDown = { tempoViewModel.downBpm() },
                    onClickUp = { tempoViewModel.upBpm() }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.tertiary,
                    text = stringResource(Res.string.tempo)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    UpDownSwitch(
                        currentValue = noteCount.toString(),
                        onClickDown = { tempoViewModel.downNoteCount() },
                        onClickUp = { tempoViewModel.upNoteCount() }
                    )
                    HorizontalDivider(
                        modifier = Modifier.width(30.dp),
                        color = MaterialTheme.colorScheme.tertiary,
                        thickness = 2.dp
                    )
                    UpDownSwitch(
                        currentValue = note.divided.toString(),
                        onClickDown = { tempoViewModel.downNote() },
                        onClickUp = { tempoViewModel.upNote() }
                    )
                }
            }
        }
        Row(
            modifier = Modifier.padding(top = 25.dp)
        ) {
            PlayerIcon(
                onClickPlayer = { tempoViewModel.togglePlay() },
                isPlaying = isPlaying
            )
        }
        AnimatedVisibility(isPlaying) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                TempoIndexRamps(
                    noteCount = noteCount,
                    currentIndex = currentIndex,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Now playing!")
            }
        }
    }
}

@Composable
fun TempoIndexRamps(
    noteCount: Int,
    currentIndex: Int,
    color: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
        for (i in 0 until noteCount) {
            Text(
                modifier = Modifier.padding(horizontal = 5.dp),
                text = if (i == currentIndex) {
                    "O"
                } else {
                    "X"
                },
                color = color
            )
        }
    }
}

@Composable
fun PlayerIcon(
    onClickPlayer: () -> Unit,
    isPlaying: Boolean
) =
    if (isPlaying) {
        Res.drawable.icon_stop to Res.string.content_description_stop
    } else {
        Res.drawable.icon_play to Res.string.content_description_play
    }.let {
        Image(
            modifier = Modifier
                .clickable { onClickPlayer() }
                .width(48.dp)
                .height(48.dp),
            painter = painterResource(it.first),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
            contentDescription = stringResource(it.second)
        )
    }

@Composable
fun ControllerCard(
    content: @Composable ColumnScope.() -> Unit
) = Card(
    colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ),
    content = content
)

@Composable
fun UpDownSwitch(
    currentValue: String,
    onClickUp: () -> Unit,
    onClickDown: () -> Unit
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(start = 7.dp, end = 7.dp, top = 22.dp, bottom = 22.dp)
) {
    Image(
        modifier = Modifier.clickable { onClickDown() }
            .padding(start = 10.dp, end = 10.dp)
            .width(32.dp)
            .height(32.dp),
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
        painter = painterResource(Res.drawable.icon_minus),
        contentDescription = null
    )
    Text(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
        fontSize = TextUnit(36f, TextUnitType.Sp),
        color = MaterialTheme.colorScheme.tertiary,
        text = currentValue
    )
    Image(
        modifier = Modifier.clickable { onClickUp() }
            .padding(start = 10.dp, end = 10.dp)
            .width(32.dp)
            .height(32.dp),
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
        painter = painterResource(Res.drawable.icon_add),
        contentDescription = null
    )
}
