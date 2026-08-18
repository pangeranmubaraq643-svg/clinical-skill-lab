package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SimulationStage
import com.example.data.model.TimeMode
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.MedicalTeal40
import com.example.ui.theme.WarningAmber

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Visibility

import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Warning

import androidx.compose.material.icons.filled.Lightbulb

@Composable
fun StageHeader(
    currentStage: SimulationStage,
    timeMode: TimeMode,
    remainingSeconds: Int,
    totalSpentRupiah: Long,
    hintsUnlockedCount: Int = 0,
    onOpenReview: (() -> Unit)? = null,
    onOpenClinicalHint: (() -> Unit)? = null,
    onSelectStage: ((SimulationStage) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val isEmergency = timeMode.isEmergency
    val headerContainerColor = if (isEmergency && remainingSeconds <= 60) {
        EmergencyRed.copy(alpha = 0.12f)
    } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = headerContainerColor),
        border = if (isEmergency) androidx.compose.foundation.BorderStroke(1.5.dp, EmergencyRed.copy(alpha = 0.5f)) else null
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            // Critical Emergency Alert Banner if <= 60s
            if (isEmergency && remainingSeconds <= 60 && remainingSeconds > 0) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = EmergencyRed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Alert",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "⚠️ CODE RED KRITIS! Pasien memburuk, sisa $remainingSeconds detik! Segera tentukan diagnosis & tatalaksana!",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            // Stage Name & Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Stage Badge
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isEmergency) EmergencyRed else MedicalTeal40
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isEmergency) {
                            Icon(
                                imageVector = Icons.Default.FlashOn,
                                contentDescription = "Emergency",
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(
                            text = "STAGE ${currentStage.stepIndex}/7: ${currentStage.displayName.uppercase()}",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (onOpenClinicalHint != null) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = WarningAmber.copy(alpha = 0.18f),
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .clickable { onOpenClinicalHint() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = "Hint",
                                    tint = WarningAmber,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (hintsUnlockedCount > 0) "Hint ($hintsUnlockedCount)" else "Hint",
                                    color = WarningAmber,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    if (onOpenReview != null) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .clickable { onOpenReview() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = "Review",
                                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Review",
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Timer if timed mode (minutes > 0)
                    if (timeMode.minutes > 0) {
                        val minutes = remainingSeconds / 60
                        val seconds = remainingSeconds % 60
                        val timerText = String.format("%02d:%02d", minutes, seconds)
                        val timerColor by animateColorAsState(
                            targetValue = when {
                                remainingSeconds < 60 || isEmergency -> EmergencyRed
                                remainingSeconds < 180 -> WarningAmber
                                else -> MaterialTheme.colorScheme.primary
                            }, label = "timer_color"
                        )

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = timerColor.copy(alpha = 0.18f),
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isEmergency) Icons.Default.FlashOn else Icons.Default.Timer,
                                    contentDescription = "Timer",
                                    tint = timerColor,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = timerText,
                                    color = timerColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Cost Counter Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = "Cost",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Rp ${formatRupiah(totalSpentRupiah)}",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Step Progress Bar
            LinearProgressIndicator(
                progress = { currentStage.stepIndex / 6f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MedicalTeal40,
                trackColor = MaterialTheme.colorScheme.outlineVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Stage Step Dots
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SimulationStage.values().forEach { stage ->
                    val isDone = stage.stepIndex <= currentStage.stepIndex
                    val isCurrent = stage == currentStage
                    val dotColor = when {
                        isCurrent -> MedicalTeal40
                        isDone -> MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        else -> MaterialTheme.colorScheme.outlineVariant
                    }

                    Box(
                        modifier = Modifier
                            .size(if (isCurrent) 10.dp else 8.dp)
                            .clip(CircleShape)
                            .background(dotColor)
                            .clickable(enabled = onSelectStage != null) {
                                onSelectStage?.invoke(stage)
                            }
                    )
                }
            }
        }
    }
}

private fun formatRupiah(amount: Long): String {
    return String.format("%,d", amount).replace(',', '.')
}
