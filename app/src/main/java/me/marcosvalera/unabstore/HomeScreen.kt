package me.marcosvalera.unabstore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val repository = remember { ProductoRepository() }
    val scope = rememberCoroutineScope()

    var productos by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var mostrarCard by remember { mutableStateOf(false) }

    // 🔹 Cargar productos al iniciar
    LaunchedEffect(Unit) {
        productos = repository.obtenerProductos()
    }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        "Unab Store",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFFF9900)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarCard = true },
                containerColor = Color(0xFFFF9900)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            if (productos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay productos disponibles")
                }
            } else {
                LazyColumn {
                    items(productos) { producto ->
                        ProductoItem(
                            producto = producto,
                            onDelete = {
                                scope.launch {
                                    producto.id?.let {
                                        repository.eliminarProducto(it)
                                        productos = repository.obtenerProductos()
                                    }
                                }
                            }
                        )
                    }
                }
            }

            // 🔹 Mostrar Card flotante para agregar producto
            if (mostrarCard) {
                AddProductCard(
                    onDismiss = { mostrarCard = false },
                    onProductoAgregado = {
                        scope.launch {
                            productos = repository.obtenerProductos()
                            mostrarCard = false
                        }
                    }
                )
            }
        }
    }
}
