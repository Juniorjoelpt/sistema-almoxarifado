import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import MainLayout from "../layouts/MainLayout";
import PageHeader from "../components/PageHeader";
import AppCard from "../components/AppCard";

import api from "../services/api";

function PrefeituraForm() {

    const navigate = useNavigate();

    const { id } = useParams();
    

    const [prefeitura,setPrefeitura]=useState({

        nome:"",
        cnpj:"",
        cidade:"",
        estado:"",
        ativo:true

    });

   const [logo, setLogo] = useState(null);

    useEffect(()=>{

        if(id){

            carregar();

        }

    },[]);

    async function carregar(){

        const response=

        await api.get(`/api/prefeituras/${id}`);

        setPrefeitura(response.data);

    }

    function mascaraCNPJ(valor){

        valor=valor.replace(/\D/g,"");

        valor=valor.replace(/^(\d{2})(\d)/,"$1.$2");

        valor=valor.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3");

        valor=valor.replace(/\.(\d{3})(\d)/,".$1/$2");

        valor=valor.replace(/(\d{4})(\d)/,"$1-$2");

        return valor;

    }

    async function salvar(e){

        e.preventDefault();

        if(

            !prefeitura.nome ||

            !prefeitura.cnpj ||

            !prefeitura.cidade ||

            !prefeitura.estado

        ){

            alert("Preencha todos os campos.");

            return;

        }

        try{

            let response;

		if (id) {

   		 response = await api.put(

       		 `/api/prefeituras/${id}`,

       			 prefeitura

   			 );

		} else {

    		response = await api.post(

      		  "/api/prefeituras",

       		 prefeitura

    		);

		}
		
		const prefeituraSalva = response.data;

		if (logo) {

    		const form = new FormData();

    		form.append("arquivo", logo);

    		await api.post(

       		 `/api/prefeituras/${prefeituraSalva.id}/logo`,

       		 form,

       		 {

           	 headers:{

                "Content-Type":"multipart/form-data"

            }

        }

    );

}		

            alert("Prefeitura salva com sucesso!");

            navigate("/prefeituras");

        }catch(erro){

            alert(

                erro.response?.data?.message ||

                "Erro ao salvar."

            );

        }

    }


    return(

        <MainLayout>

            <PageHeader

                titulo={

                    id

                    ?

                    "Editar Prefeitura"

                    :

                    "Nova Prefeitura"

                }

            />

            <AppCard>

                <form onSubmit={salvar}>

                    <div className="row">

                        <div className="col-md-8 mb-3">

                            <label>

                                Nome

                            </label>

                            <input

                                className="form-control"

                                value={prefeitura.nome}

                                onChange={(e)=>

                                    setPrefeitura({

                                        ...prefeitura,

                                        nome:e.target.value

                                    })

                                }

                            />

                        </div>

                        <div className="col-md-4 mb-3">

                            <label>

                                CNPJ

                            </label>

                            <input

                                className="form-control"

                                maxLength="18"

                                value={prefeitura.cnpj}

                                onChange={(e)=>

                                    setPrefeitura({

                                        ...prefeitura,

                                        cnpj:mascaraCNPJ(

                                            e.target.value

                                        )

                                    })

                                }

                            />

                        </div>

                        <div className="col-md-8 mb-3">

                            <label>

                                Cidade

                            </label>

                            <input

                                className="form-control"

                                value={prefeitura.cidade}

                                onChange={(e)=>

                                    setPrefeitura({

                                        ...prefeitura,

                                        cidade:e.target.value

                                    })

                                }

                            />

                        </div>

                        <div className="col-md-4 mb-3">

                            <label>

                                Estado

                            </label>

                            <select

                                className="form-select"

                                value={prefeitura.estado}

                                onChange={(e)=>

                                    setPrefeitura({

                                        ...prefeitura,

                                        estado:e.target.value

                                    })

                                }

                            >

                                <option value="">

                                    Selecione

                                </option>

                                {

                                    [

                                        "AC","AL","AP","AM","BA",

                                        "CE","DF","ES","GO","MA",

                                        "MT","MS","MG","PA","PB",

                                        "PR","PE","PI","RJ","RN",

                                        "RS","RO","RR","SC","SP",

                                        "SE","TO"

                                    ].map(uf=>

                                        <option key={uf} value={uf}>

                                            {uf}

                                        </option>

                                    )

                                }

                            </select>

                        </div>

                    </div>

                    <hr/>
			<div className="mb-3">

   			 <label>Logo da Prefeitura (PNG)</label>

   			 <input
      			  type="file"
       			 className="form-control"
        			accept="image/png"
        		onChange={(e)=>setLogo(e.target.files[0])}
    			/>

			</div>

                    <button

                        className="btn btn-gold me-2"

                    >

                        Salvar

                    </button>

                    <button

                        type="button"

                        className="btn btn-secondary"

                        onClick={()=>navigate("/prefeituras")}

                    >

                        Cancelar

                    </button>
		

                </form>

            </AppCard>

        </MainLayout>

    );

}

export default PrefeituraForm;