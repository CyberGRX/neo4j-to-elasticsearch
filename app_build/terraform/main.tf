module "build_pipe" {
  source           = "git::ssh://git@github.com/CyberGRX/terraform-aws-grx-codebuild.git?ref=v2.0.0"
  region           = var.region
  vpc_id           = data.terraform_remote_state.vpc.outputs.vpc_id[var.env]
  private_subnets  = data.terraform_remote_state.vpc.outputs.private_subnets[var.env]
  project_name     = "neo4j-to-elasticsearch"
  github_repo_name = "neo4j-to-elasticsearch"
  create_pipe      = "yes"
  include_ecr      = "yes"
  env              = "core"

  parameter_list = [
    "/build/secrets/neo4j-to-elasticsearch",
    "/codebuild/nexus/helm-password",
    "/sentry/auth-token",
  ]

  providers = {
    aws.core = aws.core
  }
}

