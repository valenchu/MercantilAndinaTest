package com.mercantil.example.mercantiltest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mercantil.example.mercantiltest.persistence.entity.ProductoEntity;
import com.mercantil.example.mercantiltest.persistence.repository.PedidoDetalleRepository;
import com.mercantil.example.mercantiltest.service.dto.CrearPedidoDto;
import com.mercantil.example.mercantiltest.service.dto.DetalleCrearProductoPedidoDto;
import com.mercantil.example.mercantiltest.service.dto.DetalleProductoPedidoResponseDto;
import com.mercantil.example.mercantiltest.service.dto.PedidoResponseDtoPadre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MercantiltestApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoDetalleRepository pedidoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testCrearListaProductos() throws Exception {
        List<ProductoEntity> productos = Arrays.asList(
                new ProductoEntity(UUID.fromString("89efb206-2aa6-4e21-8a23-5765e3de1f31"), "Jamón y morrones", "Pizza de jamón y morrones", "Mozzarella, jamón, morrones y aceitunas verdes", 550.00),
                new ProductoEntity(UUID.fromString("e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1"), "Palmitos", "Pizza de Palmitos", "Mozzarella, palmitos y aceitunas negras", 600.00)
        );

        String jsonRequest = objectMapper.writeValueAsString(productos);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/productos/create_lista_productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();

        // Puedes agregar más aserciones según lo que esperas en la respuesta
        // Por ejemplo, verificar si el cuerpo de la respuesta contiene ciertos elementos

        // Ejemplo de aserción usando JSONAssert (puedes ajustar según tus necesidades)
        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertEquals(jsonRequest, jsonResponse, true);
    }


    @MethodSource("productoProvaide")
    @ParameterizedTest
    public void testCrearPedidoProductos(List<ProductoEntity> productos) throws Exception {


        // Crear pedidos con los productos recién creados
        List<CrearPedidoDto> pedidos = Arrays.asList(
                new CrearPedidoDto("Dorton Road 80", "tsayb@opera.com", "(0351) 48158101", "21:00", returnList(productos)),
                new CrearPedidoDto("Paso de los andes", "TestValen@opera.com", "(0351) 48158101", "19:00", returnList(productos)),
                new CrearPedidoDto("Acapulco 322", "Oquinawa@opera.com", "(2634) 48158101", "13:00", returnList(productos))
        );
        pedidos.forEach(pedi -> {
            String jsonPedidos = null;
            try {
                jsonPedidos = objectMapper.writeValueAsString(pedi);


                MvcResult resultPedidos = null;

                resultPedidos = mockMvc.perform(MockMvcRequestBuilders.post("/pedidos/crear_pedido")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonPedidos))
                        .andExpect(status().isCreated())
                        .andReturn();
                String jsonResponse = resultPedidos.getResponse().getContentAsString(StandardCharsets.UTF_8);
                assertEquals(jsonPedidos, jsonResponse, false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        });
        limpiarPedidos();
    }

    @MethodSource("productoProvaide")
    @ParameterizedTest
    public List<String> testCrearPedidoSolo(List<ProductoEntity> productos, double cantA, double cantB) throws Exception {
        AtomicReference<String> jsonResponse = new AtomicReference<>("");

        // Crear pedidos con los productos recién creados
        List<CrearPedidoDto> pedidos = Collections.singletonList(
                new CrearPedidoDto("Dorton Road 80", "tsayb@opera.com", "(0351) 48158101", "21:00", returnList(productos, cantA, cantB))
        );
        AtomicReference<MvcResult> resultPedidos = new AtomicReference<>();
        pedidos.forEach(pedi -> {
            String jsonPedidos = null;

            try {
                jsonPedidos = objectMapper.writeValueAsString(pedi);


                resultPedidos.set(mockMvc.perform(MockMvcRequestBuilders.post("/pedidos/crear_pedido")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonPedidos))
                        .andExpect(status().isCreated())
                        .andReturn());
                jsonResponse.set(resultPedidos.get().getResponse().getContentAsString(StandardCharsets.UTF_8));
                assertEquals(jsonPedidos, jsonResponse.get(), false);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        });
        List<String> strings = new ArrayList<>();
        strings.add(jsonResponse.get());
        return strings;
    }


    @Test
    public void testCrearPedidoConErrores() throws Exception {
        // Crear una solicitud con errores
        String jsonRequest = "{\n" +
                "  \"email\": \"tsayb@opera.com\",\n" +
                "  \"telefono\": \"(0351) 48158101\",\n" +
                "  \"horario\": \"21:00\",\n" +
                "  \"detalle\": [\n" +
                "    {\"producto\": \"89efb206-2aa6-4e21-8a23-5765e3de1f31\"},\n" +
                "    {\"producto\": \"e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1\", \"cantidad\": 2}\n" +
                "  ]\n" +
                "}";

        // Enviar la solicitud y verificar la respuesta
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/pedidos/crear_pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest()) // Verificar el código de estado 400
                .andReturn();

        // Verificar los mensajes de error en la respuesta
        // Ajusta el orden de los errores esperados según el nuevo orden
        String expectedResponse = "[" +
                "{\"message\":\"Error: Error: falta ingresar cantidad\",\"code\":400,\"status\":\"BAD_REQUEST\",\"messageDto\":\"detalle[0].cantidad\",\"errorDto\":null}," +
                "{\"message\":\"Error: Error: la direccion no puede estar nula\",\"code\":400,\"status\":\"BAD_REQUEST\",\"messageDto\":\"direccion\",\"errorDto\":null}," +
                "{\"message\":\"Error: Error: la direccion no puede estar vacia\",\"code\":400,\"status\":\"BAD_REQUEST\",\"messageDto\":\"direccion\",\"errorDto\":null}" +
                "]";
        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertEquals(expectedResponse, jsonResponse, true);
    }

    @ParameterizedTest
    @MethodSource("productoProvaide")
    void testListarPedidosPorFecha(List<ProductoEntity> productos) throws Exception {

        String jsonRequest = objectMapper.writeValueAsString(productos);

        mockMvc.perform(MockMvcRequestBuilders.post("/productos/create_lista_productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();
        List<String> resultCompare = testCrearPedidoSolo(productos, 3D, 4D);
        // Simula una solicitud GET para listar pedidos por fecha
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/traer_pedidos_fecha?fecha="+LocalDate.now()))
                .andExpect(status().isOk())
                .andReturn();

        // Ajusta el expected response según tu estructura de datos
        DetalleProductoPedidoResponseDto detalle1 = new DetalleProductoPedidoResponseDto("89efb206-2aa6-4e21-8a23-5765e3de1f31", "Jamón y morrones", 3.0, 550.0);
        DetalleProductoPedidoResponseDto detalle2 = new DetalleProductoPedidoResponseDto("e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1", "Palmitos", 4.0, 600.0);

        // Verifica si la respuesta coincide con el expected response
        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<JsonNode> jsonNodes = objectMapper.readValue(String.valueOf(resultCompare), new TypeReference<List<JsonNode>>() {
        });

        // Iterar sobre cada objeto JsonNode y quitar la propiedad "estado"
        for (JsonNode node : jsonNodes) {
            ((ObjectNode) node).remove("estado");
            System.out.println(node);
        }

        // Convertir la lista de JsonNode de nuevo a una cadena JSON
        String jsonSinEstado = objectMapper.writeValueAsString(jsonNodes);

        // Puedes comparar directamente los strings JSON
        JSONAssert.assertEquals(String.valueOf(jsonSinEstado), jsonResponse, true);
        testCrearPedidoProductos(productos);

    }

    /**
     * @param productos
     * @return retorna la lista
     */
    private List<DetalleCrearProductoPedidoDto> returnList(List<ProductoEntity> productos) {
        List<DetalleCrearProductoPedidoDto> detalleCrearProductoPedidoDtos = new ArrayList<>();
        detalleCrearProductoPedidoDtos.add(new DetalleCrearProductoPedidoDto(productos.get(0).getId().toString(), (double) Math.round((Math.random() * 101))));
        detalleCrearProductoPedidoDtos.add(new DetalleCrearProductoPedidoDto(productos.get(1).getId().toString(), (double) Math.round((Math.random() * 101))));
        return detalleCrearProductoPedidoDtos;
    }

    @BeforeEach
    void limpiarPedidos() {
        pedidoRepository.deleteAll(); // Elimina todos los pedidos de la base de datos
    }

    /**
     * @param productos
     * @return retorna la lista
     */
    private List<DetalleCrearProductoPedidoDto> returnList(List<ProductoEntity> productos, double cantA, double cantB) {
        List<DetalleCrearProductoPedidoDto> detalleCrearProductoPedidoDtos = new ArrayList<>();
        detalleCrearProductoPedidoDtos.add(new DetalleCrearProductoPedidoDto(productos.get(0).getId().toString(), cantA));
        detalleCrearProductoPedidoDtos.add(new DetalleCrearProductoPedidoDto(productos.get(1).getId().toString(), cantB));
        return detalleCrearProductoPedidoDtos;
    }

    private static Stream<List<ProductoEntity>> productoProvaide() throws Exception {
        return Stream.of(Arrays.asList(
                new ProductoEntity(UUID.fromString("89efb206-2aa6-4e21-8a23-5765e3de1f31"), "Jamón y morrones", "Pizza de jamón y morrones", "Mozzarella, jamón, morrones y aceitunas verdes", 550.00),
                new ProductoEntity(UUID.fromString("e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1"), "Palmitos", "Pizza de Palmitos", "Mozzarella, palmitos y aceitunas negras", 600.00)
        ));
    }

}
