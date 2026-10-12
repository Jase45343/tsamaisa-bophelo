package ls.bdsg.tsamaisa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ls.bdsg.tsamaisa.ui.theme.TsamaisaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TsamaisaTheme {
                TodayPlaceholder()
            }
        }
    }
}

/** Placeholder until the Today screen is designed. Proves the build runs on the S24. */
@Composable
fun TodayPlaceholder() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(24.dp)) {
            Text(stringResource(R.string.today_title), style = MaterialTheme.typography.headlineMedium)
            Text(stringResource(R.string.today_placeholder), style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TodayPlaceholderPreview() {
    MaterialTheme { TodayPlaceholder() }
}
