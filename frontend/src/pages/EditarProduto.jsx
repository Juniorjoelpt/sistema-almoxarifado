import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";

function EditarProduto() {

    const { id } = useParams();

    const navigate = useNavigate();
    const [fornecedores, setFornecedores] = useState([]);

    const [produto, setProduto] = useState({

        codigo: "",
        nome: "",
        categoria: null,
        unidadeEstoque: "",
	unidadeEmbalagem: "",
	fatorConversao: 1,
        estoqueAtual: 0,
        estoqueMinimo: 0

    });

    const [categorias, setCategorias] = useState([]);

    useEffect(() => {

        carregarProduto();
        carregarCategorias();
	

    }, []);

    async function carregarProduto() {

        try {

            const response =
                await api.get(`/api/produtos/${id}`);

            setProduto(response.data);

        } catch (erro) {

            console.error(erro);

            alert("Erro ao carregar produto");
        }
    }

    async function carregarCategorias() {

        try {

            const response =
                await api.get("/api/categorias");

            setCategorias(response.data);

        } catch (erro) {

            console.error(erro);
        }
    }

    function alterarCampo(e) {

    const { name, value } = e.target;

    if (name === "categoria") {

        setProduto({

            ...produto,

            categoria: {
                id: Number(value)
            }

        });

        return;
    }

    
    setProduto({

        ...produto,

        [name]:
            name === "estoqueAtual" ||
            name === "estoqueMinimo"
                ? Number(value)
                : value

    });
}
    async function salvar(e) {

        e.preventDefault();

        try {

            await api.put(
                `/api/produtos/${id}`,
                produto
            );

            alert("Produto atualizado!");

            navigate("/produtos");

        } catch (erro) {

            console.error(erro);

            alert("Erro ao atualizar produto");
        }
    }
async function carregarFornecedores() {

    try {

        const response =
            await api.get("/api/fornecedores");

        setFornecedores(response.data);

    } catch (erro) {

        console.error(erro);

    }
}
const unidades = [

"UN",
"CPR",
"CPS",
"AMP",
"FA",
"FR",
"TB",
"BG",
"ENV",
"SACHET",
"RL",
"PAR",
"CX",
"PCT",
"KG",
"L",
"LTA",
"PT",
"DZ",
"FD",
"JG",
"CJ",
"RESMA"

];

    return (

        <MainLayout>

            <h1>Editar Produto</h1>

            <form onSubmit={salvar}>
	<div className="mb-3">

    <label>Código</label>

    <input
        type="text"
        name="codigo"
        className="form-control"
        value={produto.codigo}
        onChange={alterarCampo}
    />

</div>

<div className="mb-3">

    <label>Nome</label>

    <input
        type="text"
        name="nome"
        className="form-control"
        value={produto.nome}
        onChange={alterarCampo}
    />

</div>

<div className="mb-3">

    <label>Categoria</label>

    <select
        name="categoria"
        className="form-select"
        value={produto.categoria?.id || ""}
        onChange={alterarCampo}
    >

        <option value="">
            Selecione
        </option>

        {categorias.map(categoria => (

            <option
                key={categoria.id}
                value={categoria.id}
            >
                {categoria.nome}
            </option>

        ))}

    </select>

</div>

<div className="col-md-4 mb-3">

    <label>

        Unidade de Estoque

    </label>

    <select
        className="form-select"
        value={produto.unidadeEstoque || ""}
        onChange={(e)=>
            setProduto({
                ...produto,
                unidadeEstoque:e.target.value
            })
        }
    >

        <option value="">
            Selecione
        </option>

        {unidades.map(unidade => (

            <option
                key={unidade}
                value={unidade}
            >

                {unidade}

            </option>

        ))}

    </select>

</div>

<div className="col-md-4 mb-3">

    <label>

        Fornecedor entrega em

    </label>

    <select
        className="form-select"
        value={produto.unidadeEmbalagem || ""}
        onChange={(e)=>
            setProduto({
                ...produto,
                unidadeEmbalagem:e.target.value
            })
        }
    >

        <option value="">
            Selecione
        </option>

        {unidades.map(unidade => (

            <option
                key={unidade}
                value={unidade}
            >

                {unidade}

            </option>

        ))}

    </select>
	 
	

</div>

<div className="col-md-4 mb-3">

    <label>

        Unid. por Embalagem

    </label>

    <input
        type="number"
        min="1"
        className="form-control"
        value={produto.fatorConversao || 1}
        onChange={(e)=>
            setProduto({
                ...produto,
                fatorConversao:Number(
                    e.target.value
                )
            })
        }
    />

</div>


<div className="mb-3">

    <label>Estoque Atual</label>

    <input
        type="number"
        name="estoqueAtual"
        className="form-control"
        value={produto.estoqueAtual}
        onChange={alterarCampo}
    />

</div>

<div className="mb-3">

    <label>Estoque Mínimo</label>

    <input
        type="number"
        name="estoqueMinimo"
        className="form-control"
        value={produto.estoqueMinimo}
        onChange={alterarCampo}
    />

</div>

<button
    className="btn btn-success"
>
    Salvar Alterações
</button>
            </form>

        </MainLayout>
    );
}

export default EditarProduto;