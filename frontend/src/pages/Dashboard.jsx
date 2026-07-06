import {
  FaBox,
  FaTruck,
  FaBuilding,
  FaExclamationTriangle,
  FaClipboardList,
  FaArrowDown,
  FaArrowUp,
  FaCalendarAlt,
  FaTrophy,
  FaChartPie
} from "react-icons/fa";

import { useEffect, useState } from "react";

import MainLayout from "../layouts/MainLayout";
import CardDashboard from "../components/CardDashboard";
import AppCard from "../components/AppCard";
import PageHeader from "../components/PageHeader";

import api from "../services/api";

import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    ArcElement,
    Title,
    Tooltip,
    Legend
} from "chart.js";

import {
    Line,
    Doughnut
} from "react-chartjs-2";

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    ArcElement,
    Title,
    Tooltip,
    Legend
);

function Dashboard() {

    const [dados, setDados] = useState(null);

    const [estoqueBaixo, setEstoqueBaixo] = useState([]);

    const [topProdutos, setTopProdutos] = useState([]);

    const [entradasMes, setEntradasMes] = useState(0);

    const [saidasMes, setSaidasMes] = useState(0);

    const [ultimasMovimentacoes, setUltimasMovimentacoes] = useState([]);

    const [historico, setHistorico] = useState([]);

    const [produtosValidade, setProdutosValidade] = useState([]);
    

    useEffect(() => {

        carregarResumo();
        carregarEstoqueBaixo();
        carregarTopProdutos();
        carregarGrafico();
        carregarUltimasMovimentacoes();
        carregarHistorico();
        carregarValidades();

    }, []);

    const [loaded, setLoaded] = useState(false);

    useEffect(() => {
    setTimeout(() => {
        setLoaded(true);
    }, 150);
    }, []);



    async function carregarResumo() {

        try {

            const response =
                await api.get("/api/dashboard/resumo");

            setDados(response.data);

        } catch (erro) {

            console.error(erro);

        }

    }

    async function carregarEstoqueBaixo() {

        try {

            const response =
                await api.get("/api/dashboard/estoque-baixo");

            setEstoqueBaixo(response.data);

        } catch (erro) {

            console.error(erro);

        }

    }

    async function carregarTopProdutos() {

        try {

            const response =
                await api.get("/api/dashboard/top-produtos");

            setTopProdutos(response.data);

        } catch (erro) {

            console.error(erro);

        }

    }

    async function carregarGrafico() {

        try {

            const entradas =
                await api.get("/api/dashboard/entradas-mes");

            const saidas =
                await api.get("/api/dashboard/saidas-mes");

            setEntradasMes(entradas.data || 0);

            setSaidasMes(saidas.data || 0);

        } catch (erro) {

            console.error(erro);

        }

    }

    async function carregarUltimasMovimentacoes() {

        try {

            const response =
                await api.get("/api/dashboard/ultimas-movimentacoes");

            setUltimasMovimentacoes(response.data);

        } catch (erro) {

            console.error(erro);

        }

    }

    async function carregarHistorico() {

        try {

            const response =
                await api.get("/api/dashboard/ultimos-12-meses");

            setHistorico(response.data);

        } catch (erro) {

            console.error(erro);

        }

    }

    async function carregarValidades() {

        try {

            const response =
                await api.get("/api/dashboard/proximos-vencimento");

            setProdutosValidade(response.data);

        } catch (erro) {

            console.error(erro);

        }

    }

    if (!dados) {

        return (

            <MainLayout>

                <h3>Carregando...</h3>

            </MainLayout>

        );

    }

    const graficoHistorico = {

        labels: historico.map(item => item.mes),

        datasets: [

            {

                label: "Entradas",

                data: historico.map(item => item.entradas),

                borderColor: "#198754",

                backgroundColor: "#198754",

                tension: 0.2

            },

            {

                label: "Saídas",

                data: historico.map(item => item.saidas),

                borderColor: "#dc3545",

                backgroundColor: "#dc3545",

                tension: 0.2

            }

        ]

    };
const graficoDistribuicao = {

    labels: [

        "Entradas",
        "Saídas"

    ],

    datasets: [

        {

            data: [

                entradasMes,
                saidasMes

            ],

            backgroundColor: [

                "#198754",
                "#dc3545"

            ],

            borderWidth: 3,
            borderColor: "#fff"

        }

    ]

};


    return (

        <MainLayout>

            <PageHeader
                titulo="Dashboard"
                subtitulo="Visão geral do almoxarifado"
                icone={<FaChartPie color="#c48a2a" size={24} />}
            />

            <div className={`container-fluid ${loaded ? "dashboard-fade-in" : ""}`}>

                <div className="row">

                    <CardDashboard
                        titulo="Produtos"
                        valor={dados.totalProdutos}
                        icone={<FaBox />}
                    />

                    <CardDashboard
                        titulo="Fornecedores"
                        valor={dados.totalFornecedores}
                        icone={<FaTruck />}
                    />

                    <CardDashboard
                        titulo="Setores"
                        valor={dados.totalSetores}
                        icone={<FaBuilding />}
                    />

                    <CardDashboard
                        titulo="Estoque Baixo"
                        valor={dados.estoqueBaixo}
                        icone={<FaExclamationTriangle />}
                        cor="#dc3545"
                    />

                    <CardDashboard
                        titulo="Movimentações"
                        valor={dados.totalMovimentacoes}
                        icone={<FaClipboardList />}
                    />

                    <CardDashboard
                        titulo="Entradas do Mês"
                        valor={entradasMes}
                        icone={<FaArrowDown />}
                        cor="#198754"
                    />

                    <CardDashboard
                        titulo="Saídas do Mês"
                        valor={saidasMes}
                        icone={<FaArrowUp />}
                        cor="#dc3545"
                    />

                </div>
                {/* PRIMEIRA LINHA */}

                <div className="row align-items-stretch">

                   <div className="col-lg-6 mb-4 d-flex dashboard-fade-in">

                        <AppCard className="dashboard-table-card">

                            <div className="d-flex align-items-center mb-3">
                                <FaExclamationTriangle className="text-danger me-2" />
                                <h3 className="mb-0">
                                    Produtos com Estoque Baixo
                                </h3>
                            </div>

			<div className="dashboard-table-body">

                            <table className="table table-hover">

                                <thead>

                                    <tr>
                                        <th>Produto</th>
                                        <th>Categoria</th>
                                        <th>Atual</th>
                                        <th>Mínimo</th>
                                    </tr>

                                </thead>

                                <tbody>

                                    {estoqueBaixo.map(produto => (

                                        <tr key={produto.id}>

                                            <td>{produto.nome}</td>

                                            <td>{produto.categoria?.nome}</td>

                                            <td>

                                                <span
                                                    className={
                                                        produto.estoqueAtual === 0
                                                            ? "badge bg-dark"
                                                            : "badge bg-danger"
                                                    }
                                                >
                                                    {produto.estoqueAtual}
                                                </span>

                                            </td>

                                            <td>{produto.estoqueMinimo}</td>

                                        </tr>

                                    ))}

                                </tbody>

                            </table>
			</div>
                        </AppCard>

                    </div>

                    <div className="col-lg-6 mb-4 d-flex dashboard-fade-in">

                        <AppCard className="dashboard-table-card">

                            <div className="d-flex align-items-center mb-3">
                                <FaClipboardList className="text-primary me-2" />
                                <h3 className="mb-0">
                                    Últimas Movimentações
                                </h3>
                            </div>

			<div className="dashboard-table-body">

                            <table className="table table-hover">

                                <thead>

                                    <tr>
                                        <th>Produto</th>
                                        <th>Tipo</th>
                                        <th>Quantidade</th>
                                        <th>Data</th>
                                    </tr>

                                </thead>

                                <tbody>

                                    {ultimasMovimentacoes.map(mov => (

                                        <tr key={mov.id}>

                                            <td>{mov.produto?.nome}</td>

                                            <td>
                                                {mov.tipo === "ENTRADA"
                                                    ? "Entrada"
                                                    : "Saída"}
                                            </td>

                                            <td>{mov.quantidade}</td>

                                            <td>
                                                {new Date(
                                                    mov.dataMovimentacao
                                                ).toLocaleString()}
                                            </td>

                                        </tr>

                                    ))}

                                </tbody>

                            </table>
			   
			      </div>

                        </AppCard>

                    </div>

                </div>

                {/* SEGUNDA LINHA */}

                <div className="row">

                   <div className="col-lg-6 mb-4 d-flex dashboard-fade-in">

                        <AppCard className="dashboard-table-card">

                            <div className="d-flex align-items-center mb-3">

                                <FaCalendarAlt className="text-warning me-2" />

                                <h3 className="mb-0">
                                    Produtos Próximos ao Vencimento
                                </h3>

                            </div>

			<div className="dashboard-table-body">


                            <table className="table table-hover">

                                <thead>

                                    <tr>
                                        <th>Produto</th>
                                        <th>Validade</th>
                                        <th>Status</th>
                                    </tr>

                                </thead>

                                <tbody>

                                    {produtosValidade.map(produto => (

                                        <tr key={produto.id}>

                                            <td>{produto.nome}</td>

                                            <td>

                                                {new Date(
                                                    produto.dataValidade
                                                ).toLocaleDateString()}

                                            </td>

                                            <td>

                                                {new Date(produto.dataValidade) < new Date() ? (

                                                    <span className="badge bg-danger">

                                                        Vencido

                                                    </span>

                                                ) : (

                                                    <span className="badge bg-warning text-dark">

                                                        Próximo ao vencimento

                                                    </span>

                                                )}

                                            </td>

                                        </tr>

                                    ))}

                                </tbody>

                            </table>

			    </div>

                        </AppCard>

                    </div>

                    <div className="col-lg-6 mb-4 d-flex dashboard-fade-in">

                        <AppCard className="dashboard-table-card">

                            <div className="d-flex align-items-center mb-3">

                                <FaTrophy className="text-warning me-2" />

                                <h3 className="mb-0">

                                    Produtos Mais Movimentados

                                </h3>

                            </div>

			<div className="dashboard-table-body">

                            <table className="table">

                                <thead>

                                    <tr>
                                        <th>#</th>
                                        <th>Produto</th>
                                        <th>Total</th>
                                    </tr>

                                </thead>

                                <tbody>

                                    {topProdutos.map((item, index) => (

                                        <tr key={index}>

                                            <td>

                                                {index === 0 && "🥇"}
                                                {index === 1 && "🥈"}
                                                {index === 2 && "🥉"}

                                                {index + 1}

                                            </td>

                                            <td>{item.produto}</td>

                                            <td>

                                                <span className="badge bg-primary">

                                                    {item.total}

                                                </span>

                                            </td>

                                        </tr>

                                    ))}

                                </tbody>

                            </table>

			    </div>

                        </AppCard>

                    </div>

                </div>

                {/* TERCEIRA LINHA */}
<div className="row">

    {/* HISTÓRICO */}
    <div className="col-lg-8 mb-4 dashboard-fade-in">

        <AppCard style={{ height: "100%" }}>

            <div className="d-flex align-items-center mb-3">
                <FaClipboardList className="text-primary me-2" />
                <h3 className="mb-0">
                    Histórico de Movimentações
                </h3>
            </div>

            <div style={{ height: "320px" }}>
                <Line
                    data={graficoHistorico}
                    options={{
                        responsive: true,
                        maintainAspectRatio: false,
			animation: {
            			duration: 1200,
            			easing: "easeOutQuart"
        	},
        		plugins: {
            			legend: {
                			display: true
            		}
        	}
			
                    }}
                />
            </div>

        </AppCard>

    </div>

    {/* DISTRIBUIÇÃO */}
    <div className="col-lg-4 mb-4 dashboard-fade-in">

        <AppCard style={{ height: "100%" }}>

            <div className="d-flex align-items-center mb-3">
                <FaChartPie className="text-primary me-2" />
                <h5 className="mb-0">
                    Distribuição das Movimentações
                </h5>
            </div>

            <div style={{ height: "320px" }}>
                <Doughnut
                    data={graficoDistribuicao}
                    options={{
                        responsive: true,
                        maintainAspectRatio: false,
                        cutout: "72%",
			animation: {
            			animateRotate: true,
            			animateScale: true,
            			duration: 1000
        			},
                        plugins: {
                            legend: {
                                position: "bottom"
                            },
                            tooltip: {
                                callbacks: {
                                    label(context) {
                                        return `${context.label}: ${context.raw}`;
                                    }
                                }
                            }
                        }
                    }}
                />
            </div>

        </AppCard>

    </div>

</div>


                    

                </div>

           
        </MainLayout>

    );

}

export default Dashboard;